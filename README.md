This project is intended to analyze an audio wave and determine what song the wave originates from.

A wave is analyzed as input, broken into a byte array, a fast fourier transform is applied to it, then the result is hashed. It is then compared against other hashed full songs in the database, which will be collected from local .wav files.