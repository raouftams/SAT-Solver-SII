package app;
import app.blindSearch.BlindSearch;

public class Main {

    public static void main(String[] args){
        String filePath = "C:\\Users\\Raouftams\\IdeaProjects\\SAT-Solver-SII\\src\\app\\uf75-0100.cnf";
        ClausesSet clset = new ClausesSet(filePath);
        System.out.println(clset.toString());
        Solution solution = BlindSearch.DepthFirstSearch(clset);
        System.out.println(solution.toString());

    }
}
