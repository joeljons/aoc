package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.List;

public class Advent25 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent25().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }


    private long calc(List<String> strs) {
        final int[] in = Util.intArray(strs);
        final int loopSize = getLoopSize(in[0]);
        long val = 1;
        for (int i = 0; i < loopSize; i++) {
            val = (val * in[1]) % 20201227;
        }

        return val;
    }

    private int getLoopSize(long target) {
        long val = 1;
        for (int i = 0; ; i++) {
            if (val == target) {
                return i;
            }
            val = (val * 7) % 20201227;
        }
    }
}