import java.io.*;
import java.lang.*;
import java.lang.reflect.*;

class RemoteObjectReference implements Serializable {
    private String host;
    private int port;
    private String name;
    private String riname; // remote interface name
    private Class<?> cls;

    public RemoteObjectReference (String host, int port, String name,
                                  Class<?> cls) {
        this.host = host;
        this.port = port;
        this.name = name;
	this.cls = cls;
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

    public Class<?> cls(){
	return this.cls;
    }

    public Object localise() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

	// Uses interface name to identify the class stub
	Class<?> c = Class.forName(this.cls.getName()+"_stub");

        Object stub =c.getConstructor(RemoteObjectReference.class)
            .newInstance((Object)this);

        return stub;
    }
}
