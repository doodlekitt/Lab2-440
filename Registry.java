class Registry {

    private static Hashtable<String, RemoteObjectReference> objects =
        new Hashtable<String, RemoteObjectReference>();

    public boolean bind(RemoteObjectReference object, String name) {
        objects.put(name, object);
        return true;
    }

    public boolean unbind(String name) {
        if (!objects.contains(name) {
            return false;
        }
        objects.remove(name);
        return true;
    }

    public RemoteObjectReference lookup(String name) {
        return null;
    }

    public String list() {
        String message = "Registry Contains:";
        for (String name : objects.keySet()) {
            message = message + name + "\n";
        }
        return message;
    }

    private static void main(String[] args) {
        Socket client = null;
        ObjectInputStream is = null;
        ObjectOutputStream os = null;
        Message message = null;
        while(true) {
            try {
                client = server.accept();
                is = new ObjectInputStream(client.getInputStream());
                os = new ObjectOutputStream(client.getOutputStream());
                os.flush();
                message = (Message)is.readObject();
                processMessage(message);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    private static void processMessage(Message message) {

    }
}
