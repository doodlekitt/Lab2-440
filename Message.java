import java.io.*;
import java.net.Socket;

public class Message {

    public static void send(Object message, Socket socket) throws IOException {
        ObjectOutputStream os =
            new ObjectOutputStream(socket.getOutputStream());
        os.flush();
        os.writeObject(message);
    }

    public static Object recieve(Socket socket) throws IOException {
        Object response = null;

        try {
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            response = (Object)is.readObject();
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }

        return response;
    }

    public static class RegistryCommand implements Serializable {
        public enum Command {
            BIND, UNBIND, LOOKUP, LIST;
        }

        private Command command;
        private String name;
        private RemoteObjectReference ref;

    // For BIND
    public RegistryCommand (Command com, RemoteObjectReference ref) {
        this.command = com;
        this.ref = ref;
    }

    // For UNBIND and LOOKUP
    public RegistryCommand (Command com, String name) {
        this.command = com;
        this.name = name;
    }

    // For LIST
    public RegistryCommand (Command com) {
        this.command = com;
    }

    // Accessors
    public Command command() {
        return this.command;
    }

    public RemoteObjectReference ref(){
        return this.ref;
    }

    public String name() {
        return this.name;
    }
}

public static class RegistryReply implements Serializable {
    String[] names;
    RemoteObjectReference ref;

    // For BIND and UNBIND RESPONSE
    public RegistryReply() {}

    // For LIST RESPONSE
    public RegistryReply (String[] names){
        this.names = names;
    }

    // For LOOKUP RESPONSE
    public RegistryReply (RemoteObjectReference ref){
         this.ref = ref;
    }

    // Accessors
    public RemoteObjectReference ref(){
        return this.ref;
    }

    public String[] names(){
        return this.names;
    }
}

    public static class ProxyCommand implements Serializable {
        private String name;
        private String method;
        private Object[] args = null;

        public ProxyCommand(String name, String method, Object[] args) {
            this.name = name;
            this.method = method;
            this.args = args;
        }

        public String name() {
            return this.name;
        }

        public String method() {
            return this.method;
        }

        public Object[] args() {
            return this.args;
        }
    }

    public static class ProxyReply implements Serializable {
        Object returned;  // The value returned

        public ProxyReply(Object returned) {
            this.returned = returned;
        }

        public Object returned() {
            return this.returned;
        }
    }

}
