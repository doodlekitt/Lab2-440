import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

class ProxyDispatcher {
    private static int port;
    private static ServerSocket server;

    private static HashMap<String, Object> objects =
        new HashMap<String, Object>();

    public ProxyDispatcher(int port) throws IOException {
        this.port = port;
        this.server = new ServerSocket(port);
        Accept accept = new Accept();
        accept.run();
    }

    public void bind(RemoteObjectReference ref) {
        objects.put(ref.name(), ref);
    }

    public void unbind(String name) {
        objects.remove(name);
    }

    private static class Accept implements Runnable {

        private static boolean flag = true;

        public void stop() {
            flag = false;
        }

        public void run() {
            Socket client = null;
            ObjectInputStream is = null;
            ObjectOutputStream os = null;
            Message.ProxyCommand task = null;
            Message.ProxyReply response = null;
            while(flag) {
                try {
                    client = server.accept();
                    task = (Message.ProxyCommand)Message.recieve(client);
                    response = execute(task);
                    Message.send(response, client);
                    // Expects stub to close socket
                } catch (IOException | ClassNotFoundException e) {
                    // Don't print exception when stopping thread
                    if(flag) {
                        System.out.println(e);
                    }
                }
            }
        }

        public static void main(String ags[]) {
            (new Thread(new Accept())).start();
        }
    }

    private static Message.ProxyReply execute(Message.ProxyCommand task) {
        // NYI
        // TODO: Execute some method on one of the objects in objects
        return null;
    }
}
