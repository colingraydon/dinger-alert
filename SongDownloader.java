import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;



//    /Users/colingraydon/Desktop/FingerprintTest
// /Users/colingraydon/Desktop/FingerprintTest/Metallica Master Of Puppets.wav
public class SongDownloader {

    private HashMap<Long, ArrayList<Fingerprint>> allSongs;
    //this may need to be updated.
    //2 channels * 16 bit samples * 2 bytes, for 1 sec, is 176000 bytes
    //chunkzise should be 176000 for now
    final int CHUNK_SIZE = 176000;

    public SongDownloader() {
        allSongs = new HashMap<Long, ArrayList<Fingerprint>>();
    }

    public byte[] getSingleSong(String filePath) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath))) {
            int read;
            byte[] buff = new byte[1024];

            while ((read = in.read(buff)) > 0)
            {
                out.write(buff, 0, read);
            }
        }
        out.flush();
        byte[] audioBytes = out.toByteArray();
        return audioBytes;
    }

    //This method passes through the folder holding all the songs, gets their names, and runs method getSingleSong on all of them.
    //It then maps the songname to the complex array after obtaining the byte array and converting it to a complex array
    public void getAllSongs(String folderString) throws IOException{

        File folder = new File(folderString);
        File[] songList = folder.listFiles();
        for (File file : songList) {

            //gets the name of each song
            String name = file.getName();
            String songPath = folderString + name;
            int l = name.length();
            name = name.substring(0, l-4);
            byte[] byteArr = getSingleSong(songPath);

            //breaks the byte array for the song into 1 sec chunks, runs through FFT, analyzes freq magnitude
            ByteProcessor bp = new ByteProcessor();
            Complex[][] chunkedArr = bp.chunkify(byteArr, CHUNK_SIZE);
            double[][] freqArray = bp.analyzeFreq(chunkedArr);
            long[] hashed = bp.hashFreqArray(freqArray);

            //adds the hash value as a key to value of array list of fingerprints
            for (int i = 0; i < hashed.length; i++) {
                HashMap<Long, ArrayList<Fingerprint>> m = this.getMap();
                Fingerprint tempFingerprint = new Fingerprint(name, i);

                if (!m.containsKey(hashed[i])) {
                    ArrayList<Fingerprint> temp = new ArrayList<Fingerprint>();
                    temp.add(tempFingerprint);
                    m.put(hashed[i], temp);
                }
                else {
                    ArrayList<Fingerprint> temp = m.get(hashed[i]);
                    temp.add(tempFingerprint);
                    m.put(hashed[i], temp);
                }
                
            }

        }
    }

    public void setMap(HashMap<Long, ArrayList<Fingerprint>> m) {
        this.allSongs = m;
    }

    public HashMap<Long,ArrayList<Fingerprint>> getMap() {
        return this.allSongs;
    }
    public static void main(String[] args) throws IOException {

        SongDownloader dl = new SongDownloader();
        String path = "/Users/colingraydon/Desktop/FingerprintTest/Metallica Master Of Puppets.wav";
        String folder = "/Users/colingraydon/Desktop/FingerprintTest/";
        dl.getAllSongs(folder);
    }
}
