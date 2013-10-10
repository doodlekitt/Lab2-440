import java.io.*;
import java.lang.*;
import java.net.*;
import java.util.*;

public class ExampleServer {

    private static String host;
    private static String reghost;
    private static int regport;
    private static int proxyport;

    private static HashMap<String,Object> objects = new HashMap<String,Object>();

    // Takes these as arguments
    // (0) registry host
    // (1) registry port
    // (2) proxy port to listen on
    public static void main(String[] args) throws UnknownHostException {

	if(args.length != 3){
	    System.out.println("Incorrect Arguments. Requires:");
	    System.out.println("<registryhost> <registryport> <proxyport>");
	    return;
	}

	// Get machine's host
	host = (InetAddress.getLocalHost()).getHostName();

	// Get registry host and port
	// Get port for proxy dispatcher to listen on
	reghost = args[0];
	regport = Integer.valueOf(args[1]).intValue();
	proxyport = Integer.valueOf(args[2]).intValue();
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

                if(command.startsWith("help")) {
                    String help = "";
		    help += "Available commands:\n";
                    help += "quit: safely exits the server\n";
                    help += "list: lists all objects in the registry\n";
                    help += "new: creates a new object and binds it to the registry\n";
                    System.out.print(help);
                }
                else if(command.startsWith("quit"))
		{
                    for (String name : objects.keySet()) {
                        rmi.unbind(name);;
                    }
		    break;
		}
                else if(command.startsWith("list")) {
                    System.out.println("These objects are in the Registry:");
                    String[] names = rmi.list();
                    for(int i = 0; i < names.length; i++) {
                        System.out.println(names[i]);
                    }
                }
	        else if(command.startsWith("new")) {
        	    if(commandargs.length < 4) {
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
            	    Object obj = null;
                    objects.put("name", obj);

            	    // Now attempt to make new object
		    try{
                        if(class_args.length != 0) {
			    obj = c.getConstructor(String[].class)
                                .newInstance((Object)class_args);
                        } else {
                            obj = c.newInstance();
                        }
		    } catch (Exception e) {
			System.out.println(e);
			return;
		    }

		    // Create RemoteObjectReference
		    RemoteObjectReference rem = new RemoteObjectReference
				(host, proxyport, c, name, riname);
		
		    // Bind it using RMI
		    rmi.bind(rem, obj);	    	
		}
		else {
		    System.out.println("Invalid Command");
		}
                command = null;
	    }

            // Clean up
            rmi.close();
            br.close();
	} catch (IOException e){
	    System.out.println(e);
	}
    }
}
