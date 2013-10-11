import java.util.*;

// Implements MathInter
public class MathImpl implements MathInter {
   
    private HashMap<String, Integer> variables = new HashMap<String, Integer>();

    // Returns true if contains variable
    public boolean contains(String var) {
        return variables.containsKey(var);
    }

    // Prints the currently stored variables
    public void printAll() {
        System.out.println("Printing math variables");
        for (String name : variables.keySet()) {
            System.out.println(name + ": " + variables.get(name));
        }
    }

    // Creates a new variable
    public void newVar(String var, Integer val) {
        variables.put(var, val);
    }

    // Returns the value of a variable
    public Integer getValue(String var) {
        if(!variables.containsKey(var)) {
            return null;
        }
        return variables.get(var);
    }

    // Increments the value of var by x
    public Integer addTo(String var, Integer x) {
        if(!variables.containsKey(var)) {
            return null;
        }
        variables.put(var, variables.get(var)+x);
        return variables.get(var);
    }

    // Removes the variable var
    public Integer remove(String var) {
        if(!variables.containsKey(var)) {
            return null;
        }
        return variables.remove(var);
    }
}
