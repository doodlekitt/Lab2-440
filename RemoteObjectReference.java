class RemoteObjectReference {
    private String host;
    private int port;
    private Class<?> cls;

    public RemoteObjectReference (String host, int port, Class<?> cls){
        this.host = host;
        this.port = port;
        this.cls = cls;
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
}
