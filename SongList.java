import java.io.*;

public class SongList implements Serializable
{
    String song;
    String artist;
    SongList next;

    public SongList(String s, String a, SongList n){
	this.song = s;
	this.artist = a;
	this.next = n;
    }

}
