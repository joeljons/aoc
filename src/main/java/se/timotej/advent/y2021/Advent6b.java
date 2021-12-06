package se.timotej.advent.y2021;

import java.io.IOException;
import java.util.List;

public class Advent6b {

    public static void main(String[] args) throws IOException {
        var svar = new Advent6b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long sum = 0;
        long[] g = new long[10];
        for (Integer days : Util.findAllPositiveInts(strs.get(0))) {
            g[days]++;
        }
        for (int i = 0; i < 256; i++) {
            long[] g2 = new long[10];
            for (int j = 0; j < 9; j++) {
                if (j == 0) {
                    g2[6] += g[j];
                    g2[8] += g[j];
                } else {
                    g2[j - 1] += g[j];
                }
            }
            g = g2;
        }
        for (long fiskar : g) {
            sum += fiskar;
        }
        return sum;
    }

}
