import java.io.*;
import java.net.*;
import java.util.*;

public class SongListServer_stub implements SongListServer {

    private String host; // Proxy Dispatcher host
    private int port; // Proxy Dispatcher port
    private String name; // registered name
    private String riname; // remote interface name

    private Socket stub = null;

    public SongListServer_stub(String[] args){
	this.host = args[0];
	this.port = Integer.valueOf(args[1]).intValue();
	this.name = args[2];
	this.riname = args[3];
    }

    public void initialise(SongList songs){
	String mthd = "initialise";
	Object[] args = {songs};
	Message.ProxyCommand msg =new Message.ProxyCommand(this.name,mthd,args);
    	
	//Connect to Proxy Dispatcher and send request
	try{
	    stub = new Socket(host, port);
	} catch (IOException e) {
	    System.out.println(e);
	    return;
	}
	
	Message.send(msg, stub);
	
	// receive answer, though in this case, we don't return since
	// the function is void
	Object ans = Message.recieve(stub);
    }

    public String find(String song){
	String mthd = "find";
	Object[] args = {song};
	Message.ProxyCommand msg =new Message.ProxyCommand(this.name,mthd,args);

	// Connect to Proxy Dispatcher
	try{
	    stub = new Socket(host, port);
	} catch (IOException e) {
	    System.out.println(e);
	    return;
	}	
	
	Message.send(msg, stub);
	
	// recieve and return answer
	String ans = (String) Message.recieve(stub);
	return ans;
    }

    public SongList findAll(){
	String mthd = "findAll";
	Object[] args = {};
	Message.ProxyCommand msg =new Message.ProxyCommand(this.name,mthd,args);

	// Connect to Proxy Dispatcher
	try{
	    stub = new Socket(host, port);
	} catch (IOException e) {
	    System.out.println(e);
	    return;
	}

	// Send message to Proxy Dispatcher
	Message.send(msg, stub);
	
	// Recieve and return answer
	SongList ans = Message.recieve(stub);
	return ans;
    }

    public void printAll(){
	String mthd = "printAll";
	Object[] args = {};
	Message.ProxyCommand msg =new Message.ProxyCommand(this.name,mthd,args);

	//Connect to Proxy Dispatcher
	try{
	    stub = new Socket(host, port);
	} catch (IOException e) {
	    System.out.println(e);
	    return;
	}

	// send message
	Message.send(msg, stub);
	// recieve message, don't return anything since the function is void
	Object ans = Message.recieve(stub);
    }

}
