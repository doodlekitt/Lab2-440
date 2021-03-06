import java.io.*;
import java.net.*;
import java.util.*;

public class SongListServer_stub implements SongListServerInter {

    private String host; // Proxy Dispatcher host
    private int port; // Proxy Dispatcher port
    private String name; // registered name

    private Socket stub = null;

    public SongListServer_stub(RemoteObjectReference ror){
	this.host = ror.host();
	this.port = ror.port();
	this.name = ror.name();
    }

    public void initialise(SongList songs) {
	String mthd = "initialise";
	Object[] args = {songs};
	Message.ProxyCommand msg =new Message.ProxyCommand(this.name,mthd,args);
        // Sends message;
	sendMessage(msg);
    }

    public String find(String song){
	String mthd = "find";
	Object[] args = {song};
	Message.ProxyCommand msg =new Message.ProxyCommand(this.name,mthd,args);
        String ans = null;

	// recieve and return answer
        Message.ProxyReply response = (Message.ProxyReply) sendMessage(msg);
        if (response != null)
            ans = (String) response.returned();
        return ans;
    }

    public SongList findAll(){
	String mthd = "findAll";
	Message.ProxyCommand msg =new Message.ProxyCommand(this.name,mthd);
        SongList ans = null;

        // Send message to Proxy Dispatcher and get response
        Message.ProxyReply response = sendMessage(msg);
        if (response != null)
            ans = (SongList) response.returned();

	return ans;
    }

    public void printAll(){
	String mthd = "printAll";
	Message.ProxyCommand msg =new Message.ProxyCommand(this.name,mthd);

        Message.ProxyReply ans = sendMessage(msg);
        return;
    }

    private Message.ProxyReply sendMessage(Message.ProxyCommand message) {
        Message.ProxyReply reply = null;

        // Connect to ProxyDispatcher
        try {
            stub = new Socket(host, port);

            // send message
            Message.send(message, stub);
            // recieve message
            reply = (Message.ProxyReply) Message.recieve(stub);
            stub.close();
        } catch (IOException e) {
            System.out.println(e);
        }

        return reply;
    }
}
