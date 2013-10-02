class Registry {

    private static Hashtable<String, RemoteObjectReference> objects =
        new Hashtable<String, RemoteObjectReference>();

    public boolean bind() {
        // NYI
        return false;
    }

    public boolean unbind() {
        // NYI
        return false;
    }

    public RemoteObjectReference lookup(String name) {
        return null;
    }

    public String list() {
        return "";
    }
}
