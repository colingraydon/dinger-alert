public class Fingerprint {
    
    private String song;
    private int second;
    private long hash;

    public Fingerprint() {

    }

    public Fingerprint(String song, int second, long hash) {

        this.song = song;
        this.second = second;
        this.hash = hash;
    }

    public String getSong() {
        return this.song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public int getSecond() {
        return this.second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public long getHash() {
        return this.hash;
    }

    public void setHash(long hash) {

        this.hash = hash;
    }

}
