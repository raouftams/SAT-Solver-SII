package app;

import java.util.ArrayList;

public class Clause {
    public ArrayList<Literal> literals;

    public Clause(ArrayList<Literal> literals) {
        this.literals = literals;
    }

    public ArrayList<Literal> getLiterals() {
        return literals;
    }

    public Literal getLiteral(int position){
        if((position < 0) || (position > this.literals.size()))
            return null;
        return this.literals.get(position);
    }

    public int numberOfLiterals() {
        return this.literals.size();
    }


    public boolean changeLiteral(int position, Literal literal) {
        if((position < 0) || (position >= this.literals.size()))
            return false;
        this.literals.set(position, literal);
        return true;
    }

    @Override
    public String toString() {
        String output = "Clause [literals = [";
        for (Literal l: this.literals) {
            output += l.getVar() + " ";
        }
        output += "]]";
        return output;
    }

}
