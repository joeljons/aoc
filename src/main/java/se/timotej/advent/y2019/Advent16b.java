package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.List;

public class Advent16b {

    public static void main(String[] args) throws IOException {
        long start = System.nanoTime();
        String svar = new Advent16b().calc(Online.get());
        long duration = (System.nanoTime() - start) / 1000000;
        System.out.println("duration = " + duration);
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private String calc(List<String> strs) {
        int inputLen = strs.get(0).length();
        int messageOffset = Integer.parseInt(strs.get(0).substring(0, 7));

        long[] g = new long[inputLen * 10000];
        for (int i = 0; i < inputLen; i++) {
            char c = strs.get(0).charAt(i);
            g[i] = c - '0';
        }
        for (int i = 1; i < g.length / inputLen; i++) {
            System.arraycopy(g, 0, g, inputLen * i, inputLen);
        }
        if (messageOffset < g.length / 2) {
            throw new RuntimeException("Bad luck");
        }
        long[] delsum = new long[g.length];
        for (int phase = 0; phase < 100; phase++) {
            delsum[0] = g[0];
            for (int i = 1; i < g.length; i++) {
                delsum[i] = delsum[i - 1] + g[i];
            }
            for (int i = messageOffset; i < g.length; i++) {
                g[i] = Math.abs(delsum[delsum.length - 1] - delsum[i - 1]) % 10;
            }
        }

        String svar = "";
        for (int i = 0; i < 8; i++) {
            svar += g[messageOffset + i];
        }
        return svar;
    }
}
