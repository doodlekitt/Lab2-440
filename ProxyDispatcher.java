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
    }

    public void bind(String name, Object obj) {
        objects.put(name, obj);
    }

    public void unbind(String name) {
        objects.remove(name);
    }

    private static boolean flag = true;

    // Stops the run thread
    public void stop() {
        flag = false;
        try {
            // Connect once so the server stops accepting
            Socket socket = new Socket("localhost", port);
            socket.close();
        } catch (IOException e) {
            // Do nothing for errors
        }
    }

    // Runs ProxyDispatcher
    // Listens for, prcesses, and responds to messages from stubs
    public void run() {
        Socket client = null;
        ObjectInputStream is = null;
        ObjectOutputStream os = null;
        Message.ProxyCommand task = null;
        Message.ProxyReply response = null;
        while(flag) {
            try {
                // Listens for stubs
                client = server.accept();
                task = (Message.ProxyCommand)Message.recieve(client);
                // Processes request
                response = execute(task);
                // Sends reply
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

    // Excecutes the method call from a stub and returns the result
    private static Message.ProxyReply execute(Message.ProxyCommand task) {
        Object object = objects.get(task.name());
        Method method = null;
        Class<?>[] argsclass = null;
        // Get the class for all arguements
        if(task != null && task.args() != null) {
            argsclass = new Class<?>[task.args().length];
            for(int i = 0; i < task.args().length; i++) {
                argsclass[i] = task.args()[i].getClass();
            }
        }
        Message.ProxyReply response = null;
        try {
            if(argsclass != null && argsclass.length > 0) {
                method = object.getClass().getMethod(task.method(), argsclass);
            } else {
                method = object.getClass().getMethod(task.method());
            }
            // Invoke method
            Object result = method.invoke(object, task.args());
            response = new Message.ProxyReply(result);
        
        } catch (Exception e) {
            // If the method invocation throws an error, send it back to caller
            System.out.println(e);
            response = new Message.ProxyReply(e);
        }
        return response;
    }
}
