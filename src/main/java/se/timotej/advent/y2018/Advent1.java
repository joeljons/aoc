package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.List;

public class Advent1 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent1().advent1(Online.get(1));
        System.out.println("svar = " + svar);
        //Online.submit(1, 1, svar);
    }

    private int advent1(List<String> strs) {
        int sum = 0;
        for (Integer i : Util.intArray(strs)) {
            sum += i;
        }
        return sum;
    }

}
