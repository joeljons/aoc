package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.List;

public class Advent1b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent1b().calc(Online.get(1));
        System.out.println("svar = " + svar);
        Online.submit(1, 2, svar);
    }

    private int calc(List<String> strs) {
        int sum = 0;
        for (Integer i : Util.intArray(strs)) {
            for (int s = i / 3 - 2; s >= 0; s = s / 3 - 2) {
                sum += s;
            }
        }
        return sum;
    }

}
