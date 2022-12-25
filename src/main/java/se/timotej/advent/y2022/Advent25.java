package se.timotej.advent.y2022;

import java.io.IOException;
import java.util.List;

public class Advent25 {
    public static void main(String[] args) throws IOException {
        String svar = new Advent25().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }


    private String calc(List<String> strs) {
        long sum = 0;
        for (String str : strs) {
            long decimal = fromSnafu(str);
            sum += decimal;
        }
        return toSnafu(sum);
    }

    private long fromSnafu(String str) {
        long multiplier = 1;
        long sum = 0;
        for (int i = str.length() - 1; i >= 0; i--) {
            char c = str.charAt(i);
            if (c >= '0' && c <= '2') {
                sum += (multiplier * (c - '0'));
            } else if (c == '-') {
                sum -= multiplier;
            } else if (c == '=') {
                sum -= 2 * multiplier;
            } else {
                throw new RuntimeException();
            }
            multiplier *= 5;
        }
        return sum;
    }

    private String toSnafu(long value) {
        String svar = "";
        long multiplier = 1;
        while (value > 0) {
            long num = (value % (multiplier * 5)) / multiplier;
            if (num == 0) {
                svar = "0" + svar;
            } else if (num == 1) {
                svar = "1" + svar;
                value -= multiplier;
            } else if (num == 2) {
                svar = "2" + svar;
                value -= 2 * multiplier;
            } else if (num == 3) {
                svar = "=" + svar;
                value += 2 * multiplier;
            } else if (num == 4) {
                svar = "-" + svar;
                value += multiplier;
            }
            multiplier *= 5;
        }
        return svar;
    }
}