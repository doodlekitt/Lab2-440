class RemoteObjectReference {
    private String host;
    private int port;

    public RemoteObjectReference (String host, int port){
        this.host = host;
        this.port = port;
    }

    public String host(){
        return this.host;
    }

    public int port(){
        return this.port;
    }
}
