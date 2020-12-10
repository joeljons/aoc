package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Advent10b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent10b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }


    private long calc(List<String> strs) {
        int[] ints = Util.intArray(strs);
        long[] ways = new long[200];
        Arrays.sort(ints);
        ways[0] = 1;
        for (int nu : ints) {
            for (int i = Math.max(nu - 3, 0); i < nu; i++) {
                ways[nu] += ways[i];
            }
        }
        return ways[ints[ints.length - 1]];
    }
}
