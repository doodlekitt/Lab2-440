class ProxyDispatcher {
    private static port;
    private static ServerSocket server;

    private static Hashtable<String, Object> objects =
        new Hashtable<String, Object>

    public ProxyDispatcher(int port) {
        this.port = port;
        this.server = new ServerSocket(port);
        Accept accept = new Accept();
        Thread acceptThread = new Thread(accept);
        acceptThread.start();
    }

    public void bind(String name, RemoteObjectReference ref) {
        objects.put(name, ref);
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
            StubMessage task = null;
            StubMessage response = null;
            while(flag) {
                try {
                    client = server.accept();
                    is = new ObjectInputStream(client.getInputStream());
                    os = new ObjectOutputStream(client.getOutputStream());
                    os.flush();
                    task = (StubMessage)is.readObject();
                    response = execute(task);
                    os.writeObject(response);
                    // Expects stub to close streams after finishing 
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

    private execute(StubMessage task) {
        // NYI
        // TODO: Execute some method on one of the objects in objects
    }
}
