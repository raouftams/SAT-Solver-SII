package app;

import gui.Frame;

public class Main {

    public static void main(String[] args){
        Frame frame = new Frame("SAT Solver");
        frame.setVisible(true);

        //Change the file path
        /*String filePath = "C:\\Users\\Raouftams\\IdeaProjects\\SAT-Solver-SII\\src\\app\\uf75-0100.cnf";

        ClausesSet clset = new ClausesSet(filePath);
        long startTime = System.currentTimeMillis();
        Solution solution = BlindSearch.DepthFirstSearch(clset);

        System.out.println(solution.toString());
        System.out.println(System.currentTimeMillis() - startTime);
        */


    }
}
