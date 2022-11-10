import java.util.ArrayList;
import java.util.HashMap;

public class SongComparator {
    

    //use treemap here? log(n) entry time but have to perform n operations
    //should work out to be the same either way
    //actually, it's better for poorly matched songs and worse for well matched. reevaluate later
    private HashMap<String, Integer> possibleSongs;
    //this may need to be updated.
    //2 channels * 16 bit samples * 2 bytes, for 1 sec, is 176000 bytes
    //chunkzise should be 176000 for now
    final int CHUNK_SIZE = 176000;

    public SongComparator() {
        
        possibleSongs = new HashMap<String, Integer>();
    }

    public void score(byte[] input, HashMap<Long, ArrayList<Fingerprint>> db) {
        
        ByteProcessor bp = new ByteProcessor();
        Complex[][] chunked = bp.chunkify(input, CHUNK_SIZE);
        double[][] freqArr = bp.analyzeFreq(chunked);
        long[] hashed = bp.hashFreqArray(freqArr);

        for (int i = 0; i < hashed.length; i ++) {
            Long tempFingerprint = new Long(hashed[i]);
            //check if hash already in the map
            if (db.containsKey(tempFingerprint)) {
                //get the list of all matching fingerprints
                for (Fingerprint f: db.get(tempFingerprint)) {
                    String tempSong = f.getSong();
                    int tempScore = 1;
                    //check if song already in possible list. 
                    if (possibleSongs.containsKey(tempSong)) {
                        tempScore += possibleSongs.get(tempSong);
                    }
                    possibleSongs.put(tempSong, tempScore);
                }
                //need to implement consecutive second logic here
                
            }
        }
    }

    public void printList() {
        
    }
}
