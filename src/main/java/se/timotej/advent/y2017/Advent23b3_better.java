package se.timotej.advent.y2017;

import java.io.IOException;

public class Advent23b3_better {
    public static void main(String[] args) throws IOException {
        new Advent23b3_better().calc();
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
                do {
                    if (d*e == b) {

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
                } while (e != b);
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

        //Online.submit(23, 2, String.valueOf(h));
    }
}