import java.io.*;
import java.net.*;
import java.util.*;

public class SongRList_stub implements SongRList {

    private String host; //Proxy Dispatcher host
    private int port; // Proxy Dispatcher port
    private String name; // registered name

    private Socket stub = null;

    public SongRList_stub(String[] args){
	this.host = args[0];
	this.port = Integer.valueOf(args[1]).intValue();
	this.name = args[2];
    }

    public String find(String song){
	String mthd = "find";
	Object[] args = {song};
	Message.ProxyCommand msg =new Message.ProxyCommand(this.name,mthd,args);
	String ans = null;

	//send, recieve and return answer
	Message.ProxyReply response = (Message.ProxyReply) sendMessage(msg);
	if(response != null)
	    ans = (String) response.returned();

	return ans;
    }

    public SongRList add(String s, String a){
	String mthd = "add";
	Object[] args = {s, a};
	Message.ProxyCommand msg =new Message.ProxyCommand(this.name,mthd,args);	SongRList ans = null;
	
	// Send, recieve and return answer
	Message.ProxyReply response = sendMessage(msg);
	if(response != null)
	    ans = (SongRList) response.returned();
	
	return ans;	

    }

    public SongRList next(){
	String mthd = "next";
	Object[] args = {};
	Message.ProxyCommand msg =new Message.ProxyCommand(this.name,mthd,args);
	SongRList ans = null;

	// send, recieve and return answer from Proxy Dispatcher
	Message.ProxyReply response = sendMessage(msg);
	if(response != null)
	    ans = (SongRList) response.returned();
	
	return ans;
    }

    private Message.ProxyReply sendMessage(Message.ProxyCommand message){
	Message.ProxyReply reply = null;

	// Connect to Proxy Dispatcher
	try {
	    stub = new Socket (host, port);
	    //send message
	    Message.send(message, stub);
	    //recieve reply
	    reply = (Message.ProxyReply) Message.recieve(stub);
	    stub.close();

	} catch (IOException e) {
	    System.out.println(e);
	}
	return reply;
    }

}
