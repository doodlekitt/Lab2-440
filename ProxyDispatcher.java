import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
                    client.close();
                } catch (IOException e) {
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
        Object object = objects.get(task.name());
        Method method = null;
        Class<?>[] argsclass = null;
        if(task != null && task.args() != null) {
            argsclass = new Class<?>[task.args().length];
            for(int i = 0; i < task.args().length; i++) {
                argsclass[i] = task.args()[i].getClass();
            }
        }
        try {
            if(argsclass == null) {
                method = object.getClass().getMethod(task.method());
            } else {
                method = object.getClass().getMethod(task.method(), argsclass);
            }
        } catch (NoSuchMethodException e) {
            // TODO: Send back to stub
            System.out.println(e);
            return null;
        }
        // Invoke method
        Object response = null;
        try {
            response = method.invoke(object, task.args());
        } catch (IllegalAccessException | InvocationTargetException e) {

            System.out.println(e);
            return null;  // TODO: Exception handling
        }
        return new Message.ProxyReply(response);
    }
}
