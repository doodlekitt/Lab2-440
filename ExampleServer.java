import java.util.*;
import java.net.*;
import java.io.*;
import java.lang.*;

public class ExampleServer {

    private static String host;
    private String reghost;
    private int regport;
    private int proxyport;

    // Takes these as arguments
    // (0) registry host
    // (1) registry port
    // (2) proxy port to listen on
    public ExampleServer(String[] args) throws UnknownHostException {

	if(args.length != 3){
	    System.out.println("Incorrect Arguments. Requires:");
	    System.out.println("<registryhost> <registryport> <proxyport>");
	    return;
	}

	// Get machine's host
	this.host = (InetAddress.getLocalHost()).getHostName();

	// Get registry host and port
	// Get port for proxy dispatcher to listen on
	this.reghost = args[0];
	this.regport = Integer.valueOf(args[1]).intValue();
	this.proxyport = Integer.valueOf(args[2]).intValue();

        try {
	    // Create RMI to handle proxy dispatching and object binding
            RMI rmi = new RMI(reghost, regport, proxyport);

            // Read in from commandline to create and bind new objects
            BufferedReader br =
                new BufferedReader(new InputStreamReader(System.in));
            String command = null;
            String[] commandargs = null;

	    while(true){
		System.out.print(" > ");
		command = br.readLine();

		commandargs = command.split(" ");

		if(command.startsWith("quit"))
		{
		    break;
		}
	        if(command.startsWith("new")) {
        	    if(commandargs.length != 3) {
               		System.out.println("Expecting command of form:");
                	System.out.println
				("new <Class> <name> <riname> <arguments>");
                	return;
             	    }   
            	    // check if class is valid
            	    Class<?> c = null;
            	    try{
                	c = Class.forName(commandargs[1]);
            	    } catch(ClassNotFoundException e){
                	System.out.println("Invalid Class");
                	return;
            	    }
				
		    // Extract object name
		    String name = commandargs[2];

		    // Extract remote interface name
		    String riname = commandargs[3];

		    // Separate arguments
		    // do I need to check this?
            	    String[] class_args = 
			Arrays.copyOfRange(commandargs, 4, commandargs.length);
            	    Object ob = null;

            	    // Now attempt to make new object
		    try{
			ob = c.getConstructor(String[].class)
			     .newInstance((Object)class_args);
		    } catch (Exception e) {
			System.out.println(e);
			return;
		    }

		    // Create RemoteObjectReference
		    RemoteObjectReference rem = new RemoteObjectReference
				(this.host, this.proxyport, c, name, riname);
		
		    // Bind it using RMI
		    rmi.bind(rem);	    	
		}
		else {
		    System.out.println("Invalid Command");
		}
	    }

            // Clean up
            br.close();
	} catch (IOException e){
	    System.out.println(e);
	}
    }


}
