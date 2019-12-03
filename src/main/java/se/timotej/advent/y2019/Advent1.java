package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.List;

public class Advent1 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent1().calc(Online.get(1));
        System.out.println("svar = " + svar);
        Online.submit(1, 1, svar);
    }

    private int calc(List<String> strs) {
        int sum = 0;
        for (Integer i : Util.intArray(strs)) {
            sum += i / 3 - 2;
        }
        return sum;
    }

}
