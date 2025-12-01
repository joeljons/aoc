package se.timotej.advent.y2025;

import java.io.IOException;
import java.util.List;

public class Advent1 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent1().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int svar = 0;
        int dial = 50;
        for (String str : strs) {
            int go = Integer.parseInt(str.substring(1));
            go *= str.charAt(0) == 'R' ? 1 : -1;
            dial = (dial + go + 1000) % 100;
            if (dial == 0) {
                svar++;
            }
        }
        return svar;
    }
}
