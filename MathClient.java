import java.io.*;
import java.util.*;

public class MathClient {

    // Takes these as arguments
    // (0) registry host
    // (1) registry port
    // (2) proxy port to listen on
    // (3) name of object to use
    
    public static void main (String[] args) throws IOException{

        if(args.length != 4){
            System.out.println("Insufficient Arguments. Requires:");
            System.out.println("<registryhost> <registryport> <proxyport>" +
                                " <objectname>");
            return;
        }

        String reghost = args[0];
        int regport = Integer.valueOf(args[1]).intValue();
        int proxyport = Integer.valueOf(args[2]).intValue();
        String name = args[3];

        // Create an instance of RMI to connect to registry
        RMI rmi = null;
        RemoteObjectReference ror = null;

        try {
            rmi = new RMI(reghost, regport, proxyport);
            ror = rmi.lookup(name);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("One of your arguments was incorrect. " +
                               "Are the hosts and ports all valid?");
            System.out.println("Or perhaps Object " + name +
                               " isn't in the registry");
            if(rmi != null)
                rmi.close();
            return;
        }

        // Make stub
        MathInter maths = null;
        try {
            maths = (MathInter) ror.localise();
        } catch (Exception e) {
            System.out.println("Localise has thrown exception");
            System.out.println(e);
            return;
        }

        // Runs some simple operations
        if(!maths.contains("a")) {
            maths.newVar("a", (Integer)5);
        }
        if(!maths.contains("b")) {
            maths.newVar("b", (Integer)2);
        }
        if(!maths.contains("c")) {
            maths.newVar("c", (Integer)39);
        }

        maths.addTo("a", maths.getValue("b"));
        maths.addTo("c", maths.getValue("a"));

        System.out.println("The value of a is " + maths.getValue("a"));
        System.out.println("The value of b is " + maths.getValue("b"));
        System.out.println("The value of c is " + maths.getValue("c"));

        maths.remove("b");

        // prints all variables on remote end
        maths.printAll();

        rmi.close();
        return;
    }
}
