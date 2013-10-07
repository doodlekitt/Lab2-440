import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

public class RMIClient implements Serializable {

    public static void main(String args[]) throws IOException {

	Socket reg = null;
    	String registryHost;
    	int registryPort;

	if(args.length < 2){
	    System.out.println("Expecting arguments of form:");
	    System.out.println("RMIClient <registryHost> <registryPort>");
	    return;
	}
	else {
	    registryHost = args[0];
	    registryPort = args[1];
	}

	

    }

}

