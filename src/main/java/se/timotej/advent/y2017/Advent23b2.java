package se.timotej.advent.y2017;

import java.io.IOException;

public class Advent23b2 {
    public static void main(String[] args) throws IOException {
        new Advent23b2().calc();
    }

    private void calc() {
        long a = 0;
        long b = 0;
        long c = 0;
        long d = 0;
        long e = 0;
        long f = 0;
        long g = 0;
        long h = 0;

        b = 93;
        c = b;
        b *= 100;
        b += 100000;
        c = b;
        c += 17000;

        int apa = 0;
        while (true) {
            f = 1;
            d = 2;
            do {
                e = 2;
                do {
                    g = d;
                    g *= e;
                    g -= b;
                    if (g == 0) {

                        System.out.println("apa = " + apa);
                        if(apa == 20){
                            System.out.println("a = " + a);
                            System.out.println("b = " + b);
                            System.out.println("c = " + c);
                            System.out.println("d = " + d);
                            System.out.println("e = " + e);
                            System.out.println("f = " + f);
                            System.out.println("g = " + g);
                            System.out.println("h = " + h);
                        }
                        apa++;
                        f = 0;
                    }
                    e++;
                    g = e;
                    g -= b;
                } while (g != 0);
                d++;
                g = d;
                g -= b;
            } while (g != 0);
            if (f == 0) {
                h++;
            }
            g = b;
            g -= c;
            if (g == 0) {
                break;
            }
            b += 17;
        }

        //Online.submit(23, 2, String.valueOf(h));
    }
}