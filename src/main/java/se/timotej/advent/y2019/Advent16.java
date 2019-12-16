package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.List;

public class Advent16 {

    public static void main(String[] args) throws IOException {
        long start = System.nanoTime();
        String svar = new Advent16().calc(Online.get());
        long duration = (System.nanoTime() - start) / 1000000;
        System.out.println("duration = " + duration);
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private String calc(List<String> strs) {
        long[] g = new long[strs.get(0).length()];
        for (int i = 0; i < strs.get(0).length(); i++) {
            char c = strs.get(0).charAt(i);
            g[i] = c - '0';
        }
        long[] g2 = new long[strs.get(0).length()];
        int[] pattern = new int[]{0, 1, 0, -1};
        for (int phase = 0; phase < 100; phase++) {
            for (int i = 0; i < g.length; i++) {
                int repsLeft = i+1;
                int patternI = 0;
                g2[i] = 0;
                for (int j = 0; j < g.length; j++) {
                    repsLeft--;
                    if (repsLeft == 0) {
                        repsLeft = i+1;
                        patternI = (patternI + 1) % 4;
                    }
                    g2[i] += pattern[patternI] * g[j];
                }
                g2[i] = Math.abs(g2[i]) % 10;
            }
            System.arraycopy(g2,0,g,0,g.length);
        }

        String svar = "";
        for (int i = 0; i < 8; i++) {
            svar += g[i];
        }
        return svar;
    }
}
