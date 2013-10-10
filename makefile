JCFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	ExampleServer.java \
	Message.java \
	ProxyDispatcher.java \
	Registry.java \
	RemoteObjectReference.java \
	RMI.java \
	SongList.java \
	SongListClient.java \
	SongListServer.java \
	SongListServerImpl.java \
	SongListServer_stub.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
