package se.timotej.advent.y2016;

import java.io.IOException;

public class Advent9 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent9().calc(Online.get(9).get(0));
//        int svar = new Advent9().calc("X(8x2)(3x3)ABCY");
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(String str) {
        String str2 = decompress(str);
        return str2.length();
    }

    private String decompress(String str) {
        StringBuilder sb = new StringBuilder();
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
                    for (int i = 0; i < times; i++) {
                        sb.append(substring);
                    }
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
                sb.append(c);
            }
        }
        System.out.println("sb = " + sb);
        return sb.toString();
    }

}
