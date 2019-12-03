package se.timotej.advent.y2015;

import java.io.IOException;
import java.util.Arrays;

public class Advent15 {
    public static void main(String[] args) throws IOException {
        long svar = new Advent15().calc();
        System.out.println("svar = " + svar);
        Online.submit(15, 1, String.valueOf(svar));
    }

    long calc() {
        long best = 0;
        for (int i = 0; i <= 100; i++) {
            for (int j = i; j <= 100; j++) {
                for (int k = j; k <= 100; k++) {
                    int a = i;
                    int b = j - i;
                    int c = k - j;
                    int d = 100 - k;
                    int[] g = {
                            4 * a - c,
                            -2 * a + 5 * b,
                            -b + 5 * c - 2 * d,
                            2 * d/*,
                            5 * a + 8 * b + 6 * c + b*/
                    };
                    long product = 1;
                    for (int G : g) {
                        product *= Math.max(0, G);
                    }
                    if (product > best) {
                        best = product;
                        System.out.println(a + " " + b + " " + c + " " + d);
                        System.out.println(Arrays.toString(g));
                        System.out.println("product = " + product);
                    }
                }
            }
        }
        return best;
    }
}
