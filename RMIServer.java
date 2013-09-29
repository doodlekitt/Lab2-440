import java.util.*;
import java.net.*;
import.java.io.*;
import.java.lang.*;

public class RMIServer{

    private static int port = 0;
    private static ServerSocket server = null;

    public static void main (String[] args) throws IOException {

	if(args.length != 1) {
	     System.out.println("Expecting command of form:");
	     System.out.println("ProcessManager <port>");
	     return;
	}

	// Create server socket	
	port = Integer.valueOf(args[0].intValue());
	server = new ServerSocket(port);

	System.out.println("RMI Server started");

	Socket client = null;
	ObjectInputStream is = null;
	ObjectOutputStream os = null;	

	// Run server to listen to clients
	try{
	   client = server.accept();
	   is = new ObjectInputStream(client.getInputStream());
	   os = new ObjectInputStream(client.getOutputStream());
	   os.flush();
	}
	// clean up
	server.close();

    }

}
