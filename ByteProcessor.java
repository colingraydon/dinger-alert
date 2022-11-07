public class ByteProcessor {
    
    //this may need to be updated.
    //2 channels * 16 bit samples * 2 bytes, for 1 sec, is 176000 bytes
    //chunkzise should be 176000 for now

    public ByteProcessor() {

    }

    //Takes the input array of byte data. Breaks it into chunks (1 sec for now)
    //Turns the byte array into a Complex array by adding in a 0 imaginary component
    //Runs each element through a FFT cooley-turkey to get freq domain from time domain and returns the complex array
    public static Complex[][] chunkify(byte[] data, int chunkSize) {

    
        int len = data.length;
        int numberOfChunks = len / chunkSize;
        Complex[][] chunked = new Complex[numberOfChunks][];

        for (int i = 0; i < numberOfChunks; i++) {
            Complex[] complexArr = new Complex[chunkSize];

            for (int j = 0; j < chunkSize; j++) {
                double temp = i * chunkSize + 1;
                complexArr[j] = new Complex(temp, 0);
            }
            chunked[i] = FFT.fft(complexArr);
        }

        return chunked;
    }

    //This method finds the frequency with the highest magnitude for any given chunk
    //chunk 1 is second 1, chunk 2 is second 2, etc. This is how time data is retained after the fft
    public static double[][] analyzeFreq(Complex[][] data) {

        //this array will store the frequency of the highest magnitude frequency for every chunk
        //first [] represents the chunk, second [] will contain the max magnitude freq for each of the freq
        double[][] freqArray = new double[data.length][];

        for (int i = 0; i < data.length; i ++) {

            //This array will hold the frequency with greatest magnitude in each range.
            //Initialized to 0 as this algo will deal with absolute values
            double highestFreqInRange[] = {0, 0, 0, 0, 0};

            for (int tempFreq = 40; tempFreq < 300; tempFreq++) {

                //Find the magnitude of the frequency. It may be negative at first
                //Log is taken to smooth out the curve for fuzz factor
                double magnitude = Math.log(data[i][tempFreq].abs() + 1);

                //identify the index of the frequency range of the frequency
                int index = findFrequencyRange(tempFreq);

                //Checks if the magnitude of a given frequency is the greatest of that range.
                //if so, sets it as the highest magnitude, and updates the freqArray
                if (highestFreqInRange[index] < magnitude) {
                    highestFreqInRange[index] = magnitude;
                    freqArray[i][index] = magnitude;
                }
            }
        }

        return freqArray;
    }

    //take the input of the array containing the highest magnitude frequency for each range for each chunk.
    //runs the hash function, with a fuzz factor, to produce a long variable for each chunk.
    //returns an array where each index represents the hashed value of that chunk of the song.
    public long[] hashFreqArray(double[][] input) {

        long[] hashedArr = new long[input.length];
        for (int i = 0; i < input.length; i++) {
            hashedArr[i] = hash(input[i][0], input[i][1], input[i][2], input[i][3]);
        }
        return hashedArr;
    }

    //hash with a fuzz factor
    //This will provide a fingerprint of a chunk of a song.
    public long hash(double freq1, double freq2, double freq3, double freq4) {

        int f1 = (int) freq1;
        int f2 = (int) freq2;
        int f3 = (int) freq3;
        int f4 = (int) freq4;

        //this will be used to fuzz our result. should be larger for worse quality recordings
        final int BUFFER_SIZE = 2;

        long hashValue = (f4 - (f4 % BUFFER_SIZE)) * 100000000 + (f3 - (f3 % BUFFER_SIZE))* 100000 + (f2 - (f2 % BUFFER_SIZE)) * 100+ (f1 - (f1 % BUFFER_SIZE));
        return hashValue;

    }



    //most songs range from 30 - 300 for bass to higher tones, but can go up to 30000
    //This method will find the index the freq range
    public static int findFrequencyRange(double inputFreq) {

        int UPPER_LIMIT = 30000;
        int[] range = new int[] {40, 80, 120, 180, UPPER_LIMIT};
        int i = 0;
        while(range[i] < inputFreq) {
            i++;
        }
        return i;
    }
}
