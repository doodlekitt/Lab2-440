import java.io.*;
import java.util.*;

public class SongRListClient{

    // Takes these as arguments
    // (0) registry host
    // (1) registry port
    // (2) proxy port to listen on
    // (3) name of object to use
    // (4) file to read songs and artists from

    public static void main (String[] args) throws IOException {

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

	// Create instance of RMI to connect to registry
	RMI rmi = new RMI(reghost, regport, proxyport);
	// Get ROR
	RemoteObjectReference ror = rmi.lookup(name);

	// Make stub
	SongRList srs = null;
	try{
	    srs = (SongRList) ror.localise();
	} catch (Exception e) {
	    System.out.println("Localise has thrown exception");
	    System.out.println(e);
	    return;
	}

	// Read in file and make "local" list
	SongList songs = null;
	boolean flag = true;
	while(flag){
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

	// test add
	System.out.println("Testing Add");
	temp = songs;
	SongRList stemp = srs; // stub
	while(temp != null){
	    System.out.println(temp.song);
	    System.out.println("TEST1");
	    srs = srs.add(temp.song, temp.artist);
	    temp = temp.next;
	}

	// Test find and next by printing everything
	// This also verifies the previous adds
	System.out.println("\n This is the remote list");
	System.out.println("Testing find and next");

	temp = songs;
	stemp = srs;
	while(temp != null){
	    String ans = stemp.find(temp.song);
	    System.out.println("Song: "+temp.song+" Artist: "+ans);
	    temp = temp.next;
	    stemp = stemp.next();
	}
	
	// Clean up
	rmi.close();
	return;
    }
}

