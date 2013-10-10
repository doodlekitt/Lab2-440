import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

class ProxyDispatcher implements Runnable {
    private static int port;
    private static ServerSocket server;

    private static HashMap<String, Object> objects =
        new HashMap<String, Object>();

    public ProxyDispatcher(int port) throws IOException {
        this.port = port;
        this.server = new ServerSocket(port);
      //  Accept accept = new Accept();
      //  accept.run();
    }

    public void bind(String name, Object obj) {
        objects.put(name, obj);
    }

    public void unbind(String name) {
        objects.remove(name);
    }

    private static boolean flag = true;

    public void stop() {
        flag = false;
        try {
            // Connect once so the server stops accepting
            Socket socket = new Socket("localhost", port);
            socket.close();
        }catch (IOException e) {
            // Do nothing
        }
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

    private static Message.ProxyReply execute(Message.ProxyCommand task) {
        Object object = objects.get(task.name());
        System.out.println("Found object by name of " + task.name());
        Method method = null;
        Class<?>[] argsclass = null;
        if(task != null && task.args() != null && task.args().length > 0) {
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
