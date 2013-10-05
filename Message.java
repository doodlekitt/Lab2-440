import java.io.*;
import java.lang.reflect.Constructor;

public class Message {

    /* NEW: Creates a new process
 *      * MIGRATE: Migrates an existing process to another slave
 *           * START: Starts a process from a file
 *                */
    public enum Command {
        BIND, UNBIND, LOOKUP, LIST;
    }

    public static class PMPackage implements Serializable{





    }
}
