package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.List;

public class Advent2b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent2b().calc(Online.get(2));
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int sum = 0;
        for (String str : strs) {
            String[] split = str.split("[ :-]+");
            int min = Integer.parseInt(split[0]);
            int max = Integer.parseInt(split[1]);
            String contain = split[2];
            String password = split[3];

            int antal = 0;
            if (password.charAt(min - 1) == contain.charAt(0)) {
                antal++;
            }
            if (password.charAt(max - 1) == contain.charAt(0)) {
                antal++;
            }
            if (antal == 1) {
                sum++;
            }
        }
        return sum;
    }

}
