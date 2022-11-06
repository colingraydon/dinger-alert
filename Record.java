import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Target;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import java.io.OutputStream;


//This class will obtain the byte array from system sounds of currently playing music

public class Record {

    final int sampleRate = 44100;
    final int sampleSizeInBits = 16;
    final int channels = 1;
    final boolean signed = true;
    final boolean bigEndian = true;

    public Record() {

    }


    public static AudioFormat setAudioFormat() {
        final int sampleRate = 44100;
        final int sampleSizeInBits = 16;
        final int channels = 1;
        final boolean signed = true;
        final boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        return format;

    }

    public byte[] openLine() throws LineUnavailableException{

        //
        AudioFormat format = setAudioFormat(); 
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
        byte [] data = new byte[line.getBufferSize() / 5];

        //Opens the line and starts recording
        line.open(format);
        line.start();

        //Creates an outputStream to store byte data
        OutputStream out = new ByteArrayOutputStream();

        long start = System.currentTimeMillis();
        long end = start + 2 * 1000;

        //records for a set amount of time. this can be changed later if desired
        try 
        {
            while (System.currentTimeMillis() < end) {
                int count = line.read(data, 0, data.length);
                if (count > 0) {
                    out.write(data, 0, count);
                }
            }
            out.close();
        } catch (IOException e) {
            System.err.println("I/O issue" + e);
            System.exit(-1);
        }

        return data;
        
    }

    public static void main(String[] args) throws LineUnavailableException {
        Record test = new Record();
        test.openLine();
    }
     
}

