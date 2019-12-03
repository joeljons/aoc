package se.timotej.advent.y2015;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Advent17b {
    public static void main(String[] args) throws IOException {
        int svar = new Advent17b().calc2(Online.get(17));
        System.out.println("svar = " + svar);
         Online.submit(17, 2, svar);
    }

    int[] g = new int[1000];

    int calc(List<String> strs) {
        Arrays.fill(g, Integer.MAX_VALUE);
        g[0] = 0;
        for (int size : Util.intArray(strs)) {
            for (int i = 150; i >= 0; i--) {
                if (g[i] != Integer.MAX_VALUE) {
                    g[i + size] = Math.min(g[i] + 1, g[i + size]);
                }
            }
        }
        System.out.println("g[150] = " + g[150]);
        System.exit(0);
        return 0;
    }

    int calc2(List<String> strs) {
        int[] ints = Util.intArray(strs);
        int svar = 0;
        for (int i0 = 0; i0 < ints.length; i0++) {
            for (int i1 = i0 + 1; i1 < ints.length; i1++) {
                for (int i2 = i1 + 1; i2 < ints.length; i2++) {
                    for (int i3 = i2 + 1; i3 < ints.length; i3++) {
                        if (ints[i0] + ints[i1] + ints[i2] + ints[i3] == 150) {
                            svar++;
                        }
                    }
                }
            }
        }
        return svar;
    }
}
