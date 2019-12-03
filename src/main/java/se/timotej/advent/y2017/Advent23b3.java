package se.timotej.advent.y2017;

import java.io.IOException;

public class Advent23b3 {
    public static void main(String[] args) throws IOException {
        new Advent23b3().calc();
    }

    private void calc() {
        long a = 0;
        long b = 0;
        long c = 0;
        long d = 0;
        long e = 0;
        long f = 0;
        long h = 0;

        b = 109300;
        c = 126300;
        while (true) {
            f = 1;
            d = 2;
            do {
                e = 2;
                do {
                    if (d * e != b) {
                        //System.out.println("h = " + h);
                        f = 0;
                    }
                    e++;
                } while (e != b);
                d++;
            } while (d != b);
            if (f == 0) {
                h++;
            }
            if (b != c) {
                break;
            }
            b += 17;
        }
        System.out.println("h = " + h);

        //Online.submit(23, 2, String.valueOf(h));
    }
}