package se.timotej.advent.y2021;

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
        for (int i = 3; i < ints.length; i++) {
            if (ints[i] > ints[i - 3]) {
                sum++;
            }
        }
        return sum;
    }

}
