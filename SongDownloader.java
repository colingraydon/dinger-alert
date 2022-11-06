import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Target;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import java.io.OutputStream;
import java.util.HashMap;



//    /Users/colingraydon/Desktop/FingerprintTest
// /Users/colingraydon/Desktop/FingerprintTest/Metallica Master Of Puppets.wav
public class SongDownloader {

    private HashMap<String, Complex[]> map;

    public SongDownloader() {
        map = new HashMap<String, Complex[]>();
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
            String name = file.getName();
            String songPath = folderString + name;
            int l = name.length();
            name = name.substring(0, l-4);
            byte[] byteArr = getSingleSong(songPath);
            Complex[] complexArr = Complex.byteToComplex(byteArr);
            map.put(name, complexArr);
        }
    }
    public static void main(String[] args) throws IOException {

        SongDownloader dl = new SongDownloader();
        String path = "/Users/colingraydon/Desktop/FingerprintTest/Metallica Master Of Puppets.wav";
        String folder = "/Users/colingraydon/Desktop/FingerprintTest/";
        dl.getAllSongs(folder);
    }
}
