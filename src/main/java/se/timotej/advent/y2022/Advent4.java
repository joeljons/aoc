package se.timotej.advent.y2022;

import se.timotej.advent.y2021.Util;

import java.io.IOException;
import java.util.List;

public class Advent4 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent4().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int sum = 0;
        for (String str : strs) {
            List<Integer> ints = Util.findAllPositiveInts(str);
            int overlap = 0;
            if (ints.get(0) >= ints.get(2) && ints.get(1) <= ints.get(3)) {
                overlap = 1;
            }
            if (ints.get(0) <= ints.get(2) && ints.get(1) >= ints.get(3)) {
                overlap = 1;
            }
            sum += overlap;
        }
        return sum;
    }

}
