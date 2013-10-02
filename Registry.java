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
}
