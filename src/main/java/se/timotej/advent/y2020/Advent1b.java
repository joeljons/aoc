package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.List;

public class Advent1b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent1b().calc(Online.get(1));
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int sum = 0;
        int[] ints = Util.intArray(strs);
        for (int i = 0; i < ints.length; i++) {
            for (int j = i + 1; j < ints.length; j++) {
                for (int k = j + 1; k < ints.length; k++) {
                    if (ints[i] + ints[j] + ints[k] == 2020)
                        return ints[i] * ints[j] * ints[k];
                }
            }
        }
        return sum;
    }

}
