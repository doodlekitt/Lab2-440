import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

class Registry {
    // Keeps track of bound objects
    private static HashMap<String, RemoteObjectReference> objects =
        new HashMap<String, RemoteObjectReference>();

    public static void main(String[] args) throws IOException {
        // Parse args
        if(args.length != 1) {
             System.out.println("Expecting command of form:");
             System.out.println("Registry <port>");
             return;
        }

        // Create server socket
        int port = Integer.valueOf(args[0]).intValue();
        ServerSocket server = new ServerSocket(port);

        System.out.println("Registry Started");        

        // Connects to clients
        Socket client = null;
        Message.RegistryCommand message = null;
        Message.RegistryReply response = null;
        while(true) {
            try {
                client = server.accept();
                message = (Message.RegistryCommand)Message.recieve(client);
                response = processMessage(message);
                Message.send(response, client);
                // Lets client close socket
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    // Parses the message and delegates to various functions depending
    // on the command types
    private static Message.RegistryReply processMessage(Message.RegistryCommand message) {
        Message.RegistryReply response = null;
        if(message == null)
            return null; // should not occur
        switch (message.command()) {
            case BIND: response = bind(message);
                       break;
            case UNBIND: response = unbind(message);
                         break;
            case LOOKUP: response = lookup(message);
                         break;
            case LIST: response = list();
                       break;
            default: break;
        }
        return response;
    }

    public static Message.RegistryReply bind(Message.RegistryCommand message) {
        if(message.ref() == null || message.ref().name() == null) {
            System.out.println("Cannot BIND with null ROR");
            return null;
        }
        System.out.println("Binding " + message.ref().name());
        objects.put(message.ref().name(), message.ref());
        return new Message.RegistryReply();
    }

    public static Message.RegistryReply unbind(Message.RegistryCommand msg){
        if(msg.name() == null || !objects.containsKey(msg.name())){
            System.out.println("Cannot UNBIND: no object by name "
                + msg.name());
            return null;
        }
        System.out.println("Unbinding " + msg.name());
        objects.remove(msg.name());
        return new Message.RegistryReply();
    }

    public static Message.RegistryReply list() {
        String[] names = new String[objects.size()];
        int i = 0;
        for (String name : objects.keySet()) {
            names[i] = name;
            i++;
        }
        System.out.println("Listing registered names");
        return new Message.RegistryReply(names);
    }

    public static Message.RegistryReply lookup(Message.RegistryCommand message){
        if(message.name() == null || !objects.containsKey(message.name())) {
            System.out.println("Cannot lookup " + message.name());
            return null;
        }
        System.out.println("Looking up " + message.name());
        RemoteObjectReference ref = objects.get(message.name());
        return new Message.RegistryReply(ref);
    }
}
