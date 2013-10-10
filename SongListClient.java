import java.io.*;
import java.util.*;

public class SongListClient{

    // Takes these as arguments
    // (0) registry host
    // (1) registry port
    // (2) proxy port to listen on
    // (3) name of object to use
    // (4) file to read songs and artists from 


    public static void main (String[] args) throws IOException{

	if(args.length != 5){
	    System.out.println("Insufficient Arguments. Requires:");
	    System.out.println("<registryhost> <registryport> <proxyport>" + 
				" <objectname> <filename>");
	    return;
	}

	String reghost = args[0];
	int regport = Integer.valueOf(args[1]).intValue();
	int proxyport = Integer.valueOf(args[2]).intValue();
	String name = args[3];
	BufferedReader in = new BufferedReader(new FileReader(args[4]));

	// Create an instance of RMI to connect to registry
	RMI rmi = new RMI(reghost, regport, proxyport);	
	RemoteObjectReference ror = rmi.lookup(name);	

	// Make stub, need to write localise, may need to rename
	SongListServerInter sls = null;
        try { 
            sls = (SongListServerInter) ror.localise();
        } catch (Exception e) {
            System.out.println("Localise has thrown exception");
            System.out.println(e);
            return;
        }

	// Make "local" SongListServer
	SongList songs = null;
	boolean flag = true;
	// read in data from file
	while(flag) {
	    String song = in.readLine();
	    String artist = in.readLine();
	    if(song == null)
		flag = false;
	    else
		songs = new SongList(song.trim(), artist.trim(), songs);
	}	
	// final song is at the top of the list

	//Print original list for comparison
	SongList temp = songs;
	System.out.println("Original List:");
	while(temp != null){
	     System.out.println("Song: "+temp.song+ " Artist: "+temp.artist);
	     temp = temp.next;
	}
	
	// Test all functions!
	// Test initialise
	sls.initialise(songs);

	// Test find!
	System.out.println("\n Here is the list returned by find!");
	temp = songs;
	while(temp != null){
	     String res = sls.find(temp.song);
	     System.out.println("Song: "+temp.song+" Artist: "+res);
	     temp = temp.next;
	}	

	// Test findAll!
	System.out.println("\n Here is list returned by findAll");
	temp = sls.findAll();
	while(temp != null){
	    System.out.println("Song: "+temp.song+" Artist: "+temp.artist);
	    temp = temp.next;
	}
	
	// Test remote site printing with printAll
	sls.printAll();

        rmi.close();
        return;
    } 



}
