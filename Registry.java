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

    private static void main(String[] args) {
        Socket client = null;
        ObjectInputStream is = null;
        ObjectOutputStream os = null;
        Message message = null;
        Message response = null;
        while(true) {
            try {
                client = server.accept();
                is = new ObjectInputStream(client.getInputStream());
                os = new ObjectOutputStream(client.getOutputStream());
                os.flush();
                message = (Message)is.readObject();
                response = processMessage(message);
                os.writeObject(response);
                // Lets client clost streams
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    private static void processMessage(Message message) {
        Message response;
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

    public static Message bind(message) {
        if(message.name() == null)
            return null; // TODO: Return error message
        if(message.ref() == null)
            return null; // TODO: Return other error message
        objects.put(message.name(), message.ref());
        return new Message();
    }

    public static Message unbind(message) {
        if(message.name() == null || !object.containsKey(message.name())
            return null // TODO: Return error
        // TODO: Check if people are using it?  What do we do in that case?
        objects.remove(message.name());
        return new Message();

    }

    public static Message list() {
        String[] names = new String[objects.size()];
        int i = 0;
        for (String name : objects.keySet()) {
            result[i] = name;
            i++
        }
        return new Message(names);
    }

    public static Message lookup(Message message) {
        if(message.name() == null || !objects.containsKey(message.name())) {
            return null;  // TODO: Return error message
        }
        RemoteObjectReference ref = objects.get(message.name());
        // TODO: Probably a lot
        return new Message();
    }
}
