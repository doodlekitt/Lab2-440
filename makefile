JCFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        RemoteObjectReference.java \
	Message.java \
        Registry.java \
	ProxyDispatcher.java \
	RMI.java \
	ExampleServer.java \
	SongList.java \
	SongListServer.java \
	SongListServerImpl.java \
	SongListServer_stub.java \
        SongListClient.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
