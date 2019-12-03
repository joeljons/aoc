package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.List;

public class Advent2 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent2().calc(Online.get(2));
        System.out.println("svar = " + svar);
        Online.submit(2, 1, svar);
    }

    private int calc(List<String> strs) {
        int two = 0;
        int three = 0;

        for (String str : strs) {
            boolean gotTwo = false;
            boolean gotThree = false;
            for (char c = 'a'; c <= 'z'; c++) {
                int count = 0;
                for (int i = 0; i < str.length(); i++) {
                    if (c == str.charAt(i)) {
                        count++;
                    }
                }
                if (count == 2) {
                    gotTwo = true;
                }
                if (count == 3) {
                    gotThree = true;
                }
            }
            if (gotTwo) {
                two++;
            }
            if (gotThree) {
                three++;
            }
        }
        return two * three;
    }

}
