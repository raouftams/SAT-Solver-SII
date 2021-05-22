package app;

public class Literal {
    int var;
    boolean negative;

    public Literal(int var, boolean isNegative){
        this.var = var;
        this.negative = isNegative;
    }

    public int getVar(){
        return this.var;
    }

    public boolean isNegative(){
        return this.negative;
    }
}
