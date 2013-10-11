// A basic interface for doing simple mathmatical operations on variables
public interface MathInter {
    boolean contains(String var);
    Integer addTo(String var, Integer x);
    Integer getValue(String var);
    void newVar(String var, Integer x);
    void printAll();
    Integer remove(String var);
}
