public class Fingerprint {
    
    private String song;
    private int second;

    public Fingerprint() {

    }

    public Fingerprint(String song, int second) {

        this.song = song;
        this.second = second;
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

    // public long getHash() {
    //     return this.hash;
    // }

    // public void setHash(long hash) {

    //     this.hash = hash;
    // }

}
