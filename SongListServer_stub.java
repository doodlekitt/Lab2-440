import java.io.*;
import java.net.*;
import java.util.*;

public class SongListServer_stub implements SongListServer {

    private String host; // Proxy Dispatcher host
    private int port; // Proxy Dispatcher port
    private String name; // registered name
    private String riname; // remote interface name

    private

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
    }

    public String find(String song){
	String mthd = "find";
	Object[] args = {song};
	Message.ProxyCommand msg =new Message.ProxyCommand(this.name,mthd,args);

    }

    public SongList findAll(){
	String mthd = "findAll";
	Object[] args = {};
	Message.ProxyCommand msg =new Message.ProxyCommand(this.name,mthd,args);

    }

    public void printAll(){
	String mthd = "printAll";
	Object[] args = {};
	Message.ProxyCommand msg =new Message.ProxyCommand(this.name,mthd,args);

    }

}
