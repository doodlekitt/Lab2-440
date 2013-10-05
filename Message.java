import java.io.*;
import java.lang.reflect.Constructor;

public class Message {

    public enum Command {
        BIND, UNBIND, LOOKUP, LIST;
    }

    public static class Message implements Serializable{
	private Command command;
	private String name;
        private String[] names;
	private RemoteObjectReference ref;
        private Execection exception;

	// For BIND
	public Message (Command com, String name, RemoteObjectReference ref){
	    this.command = com;
	    this.name = name;
	    this.ref = ref;
	}

	// For UNBIND and LOOKUP
	public Message (Command com, String name){
	    this.command = com;
	    this.name = name;
	}

	// For LIST
	public Message (Command com){
	    this.command = com;
	}

	// For LIST RESPONSE
	public Message (Command com, String[] names){
	    this.command = com;
	    this.names = names;
	}

	// For LOOKUP RESPONSE
	public Message (Command com, String name, RemoteObjectReference ref){
	    this.command = com;
	    this.name = name;
	    this.ref = ref;
	}
	// Accessors
	public Command command(){
	    return this.command;
	}

	public String name() {
	    return this.name;
	}

	public RemoteObjectReference ref(){
	    return this.ref;
	}

	public String[] names(){
	    return this.names;
	}
    }
}
