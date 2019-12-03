package se.timotej.advent.y2016;

import java.io.IOException;

public class Advent9b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent9b().calc(Online.get(9).get(0));
//        int svar = new Advent9b().calc("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN");
        System.out.println("svar = " + svar);
        Online.submit(String.valueOf(svar));
    }

    private long calc(String str) {
        long svar = 0;
        int len = -1;
        int times = -1;
        for (int p = 0; p < str.length(); p++) {
            char c = str.charAt(p);
            if (times == -1 && len >= 0) {
                if (c == 'x') {
                    times = 0;
                } else {
                    len = 10 * len + c - '0';
                }
            } else if (times >= 0) {
                if (c == ')') {
                    String substring = str.substring(p + 1, p + 1 + len);
                    svar += times * calc(substring);
                    p += len;
                    len = -1;
                    times = -1;
                } else {
                    times = 10 * times + c - '0';
                }
            } else if (c == '(') {
                len = 0;
                times = -1;
            } else {
                svar++;
            }
        }
        return svar;
    }

}
