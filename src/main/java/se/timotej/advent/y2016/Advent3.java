package se.timotej.advent.y2016;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Advent3 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent3().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int count = 0;
        for (String str : strs) {
            int[] ints = Util.intArray(str);
            Arrays.sort(ints);
            if (ints[0] + ints[1] > ints[2]) {
                count++;
            }
        }
        return count;
    }
}