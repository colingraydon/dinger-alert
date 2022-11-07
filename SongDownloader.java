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

    private ArrayList<Song> allSongs;

    public SongDownloader() {
        allSongs = new ArrayList<Song>();
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
            //logic for breaking array into chunks and using FFT must go here
        }
    }
    public static void main(String[] args) throws IOException {

        SongDownloader dl = new SongDownloader();
        String path = "/Users/colingraydon/Desktop/FingerprintTest/Metallica Master Of Puppets.wav";
        String folder = "/Users/colingraydon/Desktop/FingerprintTest/";
        dl.getAllSongs(folder);
    }
}
