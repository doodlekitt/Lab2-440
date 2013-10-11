import java.io.*;
import java.net.*;
import java.util.*;

// The stub for MathImpl
public class MathImpl_stub implements MathInter {

    private String host; // Proxy Dispatcher host
    private int port; // Proxy Dispatcher port
    private String name; // registered name

    private Socket stub = null;

    public MathImpl_stub(RemoteObjectReference ror){
        this.host = ror.host();
        this.port = ror.port();
        this.name = ror.name();
    }

    public void printAll() {
        System.out.println("Calling printAll");
        String mthd = "printAll";
        Message.ProxyCommand msg =new Message.ProxyCommand(this.name,mthd);
        
        sendMessage(msg);
    }

    public void newVar(String var, Integer val) {
        System.out.println("Calling newVar");
        String mthd = "newVar";
        Object[] args = {var, val};
        Message.ProxyCommand msg =new Message.ProxyCommand(this.name,mthd,args);

        sendMessage(msg);
    }

    public Integer getValue(String var){
        System.out.println("Calling getValue");
        String mthd = "getValue";
        Object[] args = {var};
        Message.ProxyCommand msg =new Message.ProxyCommand(this.name,mthd,args);
        Integer ans = null;

        Message.ProxyReply response = (Message.ProxyReply) sendMessage(msg);
        if (response != null)
            ans = (Integer) response.returned();
        return ans;
    }
    public Integer addTo(String var, Integer val){
        System.out.println("Calling addTo");
        String mthd = "addTo";
        Object[] args = {var, val};
        Message.ProxyCommand msg =new Message.ProxyCommand(this.name,mthd,args);
        Integer ans = null;

        Message.ProxyReply response = (Message.ProxyReply) sendMessage(msg);
        if (response != null)
            ans = (Integer) response.returned();
        return ans;
    }

    public boolean contains(String name){
        System.out.println("Calling contains");
        String mthd = "contains";
        Object[] args = {name};
        Message.ProxyCommand msg =new Message.ProxyCommand(this.name,mthd,args);
        boolean ans = false;

        Message.ProxyReply response = (Message.ProxyReply) sendMessage(msg);
        if (response != null)
            ans = (boolean) response.returned();
        return ans;
    }

    public Integer remove(String name){
        System.out.println("Calling remove");
        String mthd = "remove";
        Object[] args = {name};
        Message.ProxyCommand msg =new Message.ProxyCommand(this.name,mthd,args);
        Integer ans = null;

        Message.ProxyReply response = (Message.ProxyReply) sendMessage(msg);
        if (response != null)
            ans = (Integer) response.returned();
        return ans;
    }

    // Sends a message to the ProxyDispatcher
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

