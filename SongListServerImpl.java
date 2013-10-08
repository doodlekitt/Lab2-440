import java.io.*;

public class SongListServerImpl implements SongListServer{

    SongList songs;

    // Constructor
    public SongListServerImpl (){
	this.songs = null;
    }

    // should be called with marshalled data sent from the stub
    public void initialise(SongList s){
	this.songs = s;
    }

    public String find(String s){
	SongList temp = songs;
	while(temp != null && !temp.song.equals(s))
	    temp = temp.next;
	
	// we either run out of songs or we have found it
	if(temp == null)
	    return null;
	else
	    return temp.artist;

    }

    // send entire song list
    public SongList findAll(){
	return songs;
    }

    // print in remote site 
    public void printAll(){
	SongList temp = songs;
	while(temp != null){
	    System.out.println ("Song: "+temp.song+", "+
				"Artist: }+temp.artist+"\n");
	    temp = temp.next;
	}
    }

}
