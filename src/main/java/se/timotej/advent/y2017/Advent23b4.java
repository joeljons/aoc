package se.timotej.advent.y2017;

import java.io.IOException;

public class Advent23b4 {
    public static void main(String[] args) throws IOException {
        new Advent23b4().calc();
    }

    private void calc() {
        long a = 1;
        long b = 0;
        long c = 0;
        long d = 0;
        long e = 0;
        long f = 0;
        long g = 0;
        long h = 0;

        b = 109300;
        c = 126300;

        int apa = 0;
        while (true) {
            f = 1;
            d = 2;
            do {
                e = 2;
                if (b % d == 0) {
                    f = 0;
                }
                d++;
            } while (d != b);
            if (f == 0) {
                h++;
            }
            if (b == c) {
                break;
            }
            b += 17;
        }
        System.out.println("h = " + h);

        Online.submit(23, 2, String.valueOf(h));
    }
}