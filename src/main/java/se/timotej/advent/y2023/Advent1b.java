package se.timotej.advent.y2023;

import java.io.IOException;
import java.util.List;

public class Advent1b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent1b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int sum = 0;
        List<String> digits = List.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine");
        for (String str : strs) {
            int firstIndex = -1;
            int lastIndex = -1;
            int first = -1;
            int last = -1;
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (Character.isDigit(c)) {
                    if (first == -1) {
                        first = c - '0';
                        firstIndex = i;
                    }
                    last = c - '0';
                    lastIndex = i;
                }
            }
            for (int i = 0; i < 9; i++) {
                int i1 = str.indexOf(digits.get(i));
                if (i1 != -1 && (i1 < firstIndex || firstIndex == -1)) {
                    firstIndex = i1;
                    first = i + 1;
                }
                int i2 = str.lastIndexOf(digits.get(i));
                if (i2 != -1 && i2 > lastIndex) {
                    lastIndex = i2;
                    last = i + 1;
                }
            }
            sum += 10 * first + last;
        }
        return sum;
    }
}
