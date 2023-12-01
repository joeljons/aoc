package se.timotej.advent.y2023;

import java.io.IOException;
import java.util.List;

public class Advent1 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent1().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int sum = 0;
        for (String str : strs) {
            int first = -1;
            int last = -1;
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (Character.isDigit(c)) {
                    if (first == -1) {
                        first = c - '0';
                    }
                    last = c - '0';
                }
            }
            sum += 10 * first + last;
        }
        return sum;
    }
}
