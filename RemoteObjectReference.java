import java.io.*;
import java.lang.*;
import java.lang.reflect.*;

class RemoteObjectReference implements Serializable {
    private String host;
    private int port;
    private String name;
    private String riname; // remote interface name

    public RemoteObjectReference (String host, int port, String name,
                                  String riname) {
        this.host = host;
        this.port = port;
        this.name = name;
	this.riname = riname;
    }

    public String host(){
        return this.host;
    }

    public int port(){
        return this.port;
    }

    public String name() {
        return this.name;
    }

    public String riname(){
	return this.riname;
    }

    public Object localise() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

	// Uses interface name to identify the class stub
	Class<?> c = Class.forName(this.riname+"_stub");

        Object stub =c.getConstructor(RemoteObjectReference.class)
            .newInstance((Object)this);

        return stub;
    }
}
