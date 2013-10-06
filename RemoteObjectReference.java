class RemoteObjectReference {
    private String host;
    private int port;
    private Class<?> cls;
    String name;

    public RemoteObjectReference (String host, int port, Class<?> cls,
                                  String name){
        this.host = host;
        this.port = port;
        this.cls = cls;
        this.name = name;
    }

    public String host(){
        return this.host;
    }

    public int port(){
        return this.port;
    }

    public Class<?> cls() {
        return this.cls;
    }

    public String name() {
        return this.name;
    }

    Object localize() {
        // NYI
        // Returns a stub... somehow
        return null;
    }
}
