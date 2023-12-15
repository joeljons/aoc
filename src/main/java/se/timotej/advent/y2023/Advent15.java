package se.timotej.advent.y2023;

import java.io.IOException;
import java.util.List;

public class Advent15 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent15().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;
        String[] split = strs.get(0).split(",");
        for (String s : split) {
            svar += hash(s);
        }
        return svar;
    }

    private int hash(String s) {
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            val += s.charAt(i);
            val *= 17;
            val %= 256;
        }
        return val;
    }
}
