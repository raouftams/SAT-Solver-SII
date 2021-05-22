package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ClausesSet {
    private ArrayList<Clause> clauses = new ArrayList<>();
    private int clauseSize;
    private int numberVariables;

    public ClausesSet(String cnfFilePath){
        this.readFile(cnfFilePath);
    }

    private String fileToString(String cnf_filePath) {
        File file = new File(cnf_filePath); //cnf_filePath
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String line;
        String[] currentClause;
        ArrayList<Literal> clauseLiterals = new ArrayList<>();
        String clause = "";

        try {
            while(! (line = br.readLine()).equalsIgnoreCase("%")) { /* Read the CNF file, line per line, until the stop character "%" */
                switch ((line = line.trim()).charAt(0)) {
                    case 'c':
                        break;
                    case 'p':
                        this.numberVariables = Integer.parseInt(line.replaceAll("[^0-9 ]", "").replaceAll("  ", " ").trim().split(" ")[0]);
                        break;
                    default:
                        clause += line.replaceAll(" 0$", "") + ",";
                }
            }
        }catch (IOException e) {
                    e.printStackTrace();
                }

        return clause;
    }


    private void readFile(String filePath){
        String clauses = this.fileToString(filePath);
        String[] clauseString = clauses.split(",");
        for (int i = 0; i < clauseString.length; i++) {
            ArrayList<Literal> literalsList = new ArrayList<>();
            String[] literalString = clauseString[i].split(" ");
            for (int j = 0; j < literalString.length; j++) {
                int value = Integer.parseInt(literalString[j]);
                Literal literal = new Literal(value, (value < 0));
                literalsList.add(literal);
            }
            this.clauses.add(new Clause(literalsList));
        }
    }


    public void addClause(Clause clause) { /* Add new clause into "clauses set" */
        if(clause.numberOfLiterals() == this.clauseSize)
            this.clauses.add(clause);
    }


    public Clause getClause(int position) { /* Get clause in position "position" */
        return this.clauses.get(position);
    }


    public int getClauseSize(){ /* Get number of literals contained in each clause */
        return this.clauseSize;
    }


    public int getNumberVariables() { /* Get number of variables defined in this SAT problem */
        return numberVariables;
    }


    public boolean changeClause(int position, Clause clause) {
        if((position < 0) || (position >= this.clauses.size()))
            return false;

        this.clauses.set(position, clause);

        return true;
    }


    public int getNumberClause() { /* Get number of clauses contained in this "clauses set" */
        return this.clauses.size();
    }

    public void addLiteralsAsClause(ArrayList<Literal> literals){
        Clause c = new Clause(literals);
        this.clauses.add(c);
        System.out.println(this.toString() + "\n dagi");
    }


    @Override
    public String toString() {
        String string = "The list of clauses is : \n";

        for (int i = 0; i < this.clauses.size(); i++) {
            string += i + "- " + this.clauses.get(i).toString()+"\n";
        }
        return string;
    }

}