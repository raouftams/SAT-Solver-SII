package app.blindSearch;

import app.ClausesSet;
import app.Solution;
import java.util.Stack;

public abstract class BlindSearch {
	public static boolean stop = false;

	//DFS Algorithme using stacks
	public static Solution DepthFirstSearch(ClausesSet clset, long executionTime) {
		Solution solution = new Solution(clset.numberOfVariables());
		Stack<NodeBlind> open = new Stack<NodeBlind>();

		Solution bestSolution = new Solution(solution);
		int actual = -1, randomLiteral;

		long startTime = System.currentTimeMillis();

		do {
			if((System.currentTimeMillis() - startTime)/1000 >= executionTime && executionTime != 0)
				return solution;

			if(! open.empty()) {
				actual = open.peek().getValue();
				solution.changeLiteral(Math.abs(actual)-1, actual);
			}
			if(solution.getActiveLiterals() == clset.numberOfVariables()) {
				do {
					solution.deleteLiteral(Math.abs(open.peek().getValue())-1);
					actual = open.pop().getChildNumber();
				}while((actual == 2) && (! open.empty()));

				continue;
			}

			randomLiteral = solution.randomLiteral(clset.numberOfVariables());

			for(int i=0; i<2; i++) {
				solution.changeLiteral(Math.abs(randomLiteral)-1, i==0 ? randomLiteral : -randomLiteral);
				if(solution.satisfiedClauses(clset) > bestSolution.satisfiedClauses(clset)) {
                    bestSolution = new Solution(solution);
                }

				if(solution.isSolution(clset)){
                    return bestSolution;
                }

				open.push(new NodeBlind(i==0 ? randomLiteral : -randomLiteral, actual, i==0 ? 2 : 1));
			}
			solution.deleteLiteral(Math.abs(randomLiteral)-1);


		}while(! open.empty());
		return bestSolution;
	}


	//DFS Algorithme using binary tree
	static class Node{
		int value;
		Node fg;
		Node fd;

		public Node(int value, Node fg, Node fd){
			this.value = value;
			this.fg = fg;
			this.fd = fd;
		}
	}

	public static Solution depthFirstSearch(ClausesSet clset){
		Node root = createSatTree(clset.numberOfVariables(), 0, 0);
		Solution solution = new Solution(clset.numberOfVariables());
		findSolution(clset, root, solution);
		return solution;
	}

	public static void findSolution(ClausesSet clset, Node root, Solution solution){
		Solution tempSolution = solution;
		if(root != null && BlindSearch.stop == false){
			if (solution.isSolution(clset)){
				BlindSearch.stop = true;
			}else{
				if (root.value != 0)
					solution.changeLiteral(Math.abs(root.value)-1, root.value);
				findSolution(clset, root.fg, solution);
				findSolution(clset, root.fd, solution);
			}
		}

	}

	private static Node createSatTree(int numberOfVars, int depth, int value){
		Node node = null;
		if (depth <= numberOfVars){
			node = new Node(value,
					createSatTree(numberOfVars, depth+1, depth+1),
					createSatTree(numberOfVars, depth+1, -(depth+1))
					);
		}
		return node;

	}



	//BFS Algorithm here
}
