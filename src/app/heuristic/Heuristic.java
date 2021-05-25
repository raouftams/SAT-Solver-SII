package app.heuristic;

import app.ClausesSet;
import app.Solution;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Heuristic {

    public static Solution AStarSearch(ClausesSet clset, long executionTime) {
        int clsetSat = clset.getNumberClause();

        ArrayList<Node> open = new ArrayList<Node>();

        Solution currentSol = new Solution(clset.numberOfVariables());
        int currentSolSat = currentSol.satisfiedClauses(clset);

        Node currentNode = new Node(currentSol, 0, clsetSat-currentSolSat);

        Solution bestSolution = new Solution(currentSol);
        int bestSolutionSat = currentSolSat;

        int randomLiteral;

        long startTime = System.currentTimeMillis();

        do {
            if((System.currentTimeMillis() - startTime)/1000 >= executionTime && executionTime != 0 )
                return bestSolution;

            Collections.sort(open);

            if(! open.isEmpty()) {
                currentNode = open.remove(0);
                currentSol = new Solution(currentNode.getSolution());
            }

            if(currentSol.getActiveLiterals() == clset.numberOfVariables())
                continue;

            randomLiteral = currentSol.randomLiteral(clset.numberOfVariables());

            for(int i=0; i<2; i++) {
                currentSol.changeLiteral(Math.abs(randomLiteral)-1, i==0 ? randomLiteral : -randomLiteral);
                currentSolSat = currentSol.satisfiedClauses(clset);

                if(currentSolSat > bestSolutionSat)
                    bestSolution = new Solution(currentSol);

                if(currentSolSat == clsetSat)
                    return bestSolution;

                open.add(new Node(new Solution(currentSol), currentNode.getSolution().sameSatisfiedClausesAsLiteral(clset,
                        i==0 ? randomLiteral : -randomLiteral), clsetSat-currentSolSat));
            }
        }while(! open.isEmpty());

        return bestSolution;
    }
}
