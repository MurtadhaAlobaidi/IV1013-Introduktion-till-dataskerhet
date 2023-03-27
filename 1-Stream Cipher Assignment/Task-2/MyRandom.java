import java.util.Random;

public class MyRandom extends Random {

    // I take inspiration with "Linear Congruential Generator" from
    // https://indico.cern.ch/event/949287/contributions/4023066/attachments/2143278/3611981/lecture8_1.pdf
    public int a = 1103515245; // multiplier
    public int b = 12345; // increment
    public int m = 1 << 31; // modulus
    public int seed = 0;

    public MyRandom(long seed) {
        this.setSeed(seed);
    }

    public int next(int bits) {
        int x0 = seed;
        int x1 = (a * x0 + b) % m;
        this.seed = x1;
        return x1 >>> (31 - bits);
    }

    public void setSeed(long seed) {
        this.seed = (int) seed;
    }
}
