import java.util.*;
import java.net.*;
import java.io.*;

// The local interface for RMI.  Run by every client.
// Exchanges messages with registry,
// and runs proxy dispatcher.
public class RMI
{ 
    // The port number and host name of the registry.
    private String host;
    private int port;
    // The Proxy Dispatcher for this client
    private ProxyDispatcher proxy;
    
    public RMI(int regPort, String regHost, int proxyPort)
    {
        this.port = regPort;
	this.host = regHost;
        this.proxy = new ProxyDispatcher(proxyPort);
    }

    public void bind(String name, RemoteObjectReference ref) {
        proxy.bind(name, ref);
        Message message = new Message(Message.Command.BIND, name, ref);
        sendMessage(message);
    }

    public void unbind(String name) {
        proxy.unbind(name);
        Message message = new Message(Message.Command.UNBIND, name);
        sendMessage(message);
    }

    public String[] list() {
        Message message = new Message(Message.Command.LIST);
        Message response = sendMessage(message);
        return response.names;
    }

    // returns the ROR from the registry
    public RemoteObjectReference lookup(String name) {
        Message message = new Message(Message.Command.LOOKUP, name);
        Message response = sendMessage(message);
        return response.ref;
    }

    // TODO: Abstract into separate class
    private Message sendMessage(Message message) {
        Socket registry = null;
        ObjectInputStream is = null;
        ObjectOutputStream os = null;
        Message response = null;
        try {
            registry = new Socket(port, host);
            is = new ObjectInputStream(registry.getInputStream());
            os = new ObjectOutputStream(registry.getOutputStream());
            os.flush();
            os.writeObject(message);
            reponse = is.readObject();
            is.close();
            os.close();
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
        return reponse;
    }
}
