import java.lang.*;

class RemoteObjectReference {
    private String host;
    private int port;
    private Class<?> cls;
    String name;
    String riname; // remote interface name

    public RemoteObjectReference (String host, int port, Class<?> cls,
                                  String name, String intr){
        this.host = host;
        this.port = port;
        this.cls = cls;
        this.name = name;
	this.riname = intr;
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

    public String riname(){
	return this.riname;
    }

    public Object localise() {

	// Uses interface name to identify the class stub
	Class c = Class.forName(this.riname+"_stub");

	// For ease of passing relevant information for sockets
	// pass along the remote object reference info as a string

	// Neglect cls as is unnecessary
	String[] args = {this.host, (this.port.toString()), this.name, this.riname };

	Object stub =c.getConstructor(String[].class).newInstance((Object)args);

        return stub;
    }
}
