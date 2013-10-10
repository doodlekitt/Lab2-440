public interface SongListServerInter {
    public void initialise(SongList songs);
    public String find(String song);
    public SongList findAll();
    public void printAll();

}
