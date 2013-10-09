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
    private Thread proxyThread;
    
    public RMI(String regHost, int regPort, int proxyPort) throws IOException
    {
        this.port = regPort;
	this.host = regHost;
        this.proxy = new ProxyDispatcher(proxyPort);
        this.proxyThread = new Thread(proxy);
        proxyThread.start();
System.out.println("New RMI");
    }

    public void bind(RemoteObjectReference ref, Object obj) {
        proxy.bind(ref.name(), obj);
        Message.RegistryCommand message = new Message.RegistryCommand(Message.RegistryCommand.Command.BIND, ref);
        sendMessage(message);
    }

    public void unbind(String name) {
        proxy.unbind(name);
        Message.RegistryCommand message = new Message.RegistryCommand(Message.RegistryCommand.Command.UNBIND, name);
        sendMessage(message);
    }

    public String[] list() {
        Message.RegistryCommand message = new Message.RegistryCommand(Message.RegistryCommand.Command.LIST);
        Message.RegistryReply response = sendMessage(message);
        return response.names;
    }

    // returns the ROR from the registry
    public RemoteObjectReference lookup(String name) {
        Message.RegistryCommand message = new Message.RegistryCommand(Message.RegistryCommand.Command.LOOKUP, name);
        Message.RegistryReply response = sendMessage(message);
        return response.ref;
    }

    private Message.RegistryReply sendMessage(Message.RegistryCommand message) {
        Message.RegistryReply response = null;
        try {
            Socket registry = new Socket(host, port);
if(message == null) { System.out.println("TEST: message null"); }
            Message.send(message, registry);
            response = (Message.RegistryReply)Message.recieve(registry);
            registry.close();
System.out.println("Sent message");
        } catch (IOException e) {
            System.out.println(e);
        }
        return response;
    }
}
