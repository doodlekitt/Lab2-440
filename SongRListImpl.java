import java.io.*;

public class SongRListImpl implements SongRList
{
    String song;
    String artist;
    SongRListImpl next;

    // constructor
    public SongRListImpl(){
	this.song = null;
	this.artist = null;
	this.next = null;
    }

    // standard constructor
    public SongRListImpl(String song, String artist, SongRListImpl next){
	this.song = song;
	this.artist = artist;
	this.next = next;
    }

    // find artist for that cell only
    // client can do a recursive search
    public String find(String s){
	if(s.equals(this.song)){
	    return this.artist;
	}	
	else
	    return null;
    }

    // Cons
    public SongRListImpl add(String s, String a){
	SongRListImpl temp = 
		new SongRListImpl(this.song, this.artist, this.next);
	return new SongRListImpl(s, a, temp);
    }

    // Go to next song-artist pair
    public SongRListImpl next(){
	return this.next;
    }

}
