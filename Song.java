public class Song {
    
    private long[] hash;
    private String name;

    public Song() {

    }

    public Song(String name, long[] hash) {
        this.name = name;
        this.hash = hash;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long[] getHash() {
        return this.hash;
    }

    public void setHash(long[] hash) {
        this.hash = hash;
    }
}
