package se.timotej.advent.y2022;

import java.io.IOException;
import java.util.List;

public class Advent1 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent1().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int best = 0;
        int sum = 0;
        for (String str : strs) {
            if (str.isBlank()) {
                best = Math.max(best, sum);
                sum = 0;
            } else {
                sum += Integer.parseInt(str);
            }
        }
        return best;
    }

}
