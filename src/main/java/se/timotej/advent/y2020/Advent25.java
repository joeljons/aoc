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
        long val0 = 1;
        long val1 = 1;
        while(val0 != in[0]) {
            val0 = (val0 * 7) % 20201227;
            val1 = (val1 * in[1]) % 20201227;
        }
        return val1;
    }
}