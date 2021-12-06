package se.timotej.advent.y2021;

import java.io.IOException;
import java.util.List;

public class Advent6 {

    public static void main(String[] args) throws IOException {
        var svar = new Advent6().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int sum = 0;
        int[] g = new int[10];
        for (Integer days : Util.findAllPositiveInts(strs.get(0))) {
            g[days]++;
        }
        for (int i = 0; i < 80; i++) {
            int[] g2 = new int[10];
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
        for (int fiskar : g) {
            sum += fiskar;
        }
        return sum;
    }

}
