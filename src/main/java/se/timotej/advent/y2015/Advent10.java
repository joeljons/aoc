package se.timotej.advent.y2015;

import java.io.IOException;

public class Advent10 {
    public static void main(String[] args) throws IOException {
        int svar = new Advent10().calc(Online.get(10).get(0));
        System.out.println("svar = " + svar);
        Online.submit(10, 1, svar);
    }

    int calc(String str) {
        for (int i = 0; i < 40; i++) {
            str = process(str);
        }
        return str.length();
    }

    private String process(String str) {
        StringBuilder ut = new StringBuilder();
        char lastc = 0;
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c != lastc) {
                if (lastc != 0) {
                    ut.append(count);
                    ut.append(lastc);
                }
                lastc = c;
                count = 1;
            } else {
                count++;
            }
        }
        if (count > 0) {
            ut.append(count);
            ut.append(lastc);
        }
        return ut.toString();
    }
}