package se.timotej.advent.y2016;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Advent3b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent3b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int count = 0;
        for (int i = 0; i < strs.size(); i += 3) {
            int[][] iints = new int[3][];
            iints[0] = Util.intArray(strs.get(i));
            iints[1] = Util.intArray(strs.get(i + 1));
            iints[2] = Util.intArray(strs.get(i + 2));
            for (int j = 0; j < 3; j++) {
                int[] ints = new int[]{iints[0][j], iints[1][j], iints[2][j]};
                Arrays.sort(ints);
                if (ints[0] + ints[1] > ints[2]) {
                    count++;
                }
            }

        }
        return count;
    }
}