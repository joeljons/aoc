package se.timotej.advent.y2025;

import java.io.IOException;
import java.util.List;

public class Advent3 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent3().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;
        for (String str : strs) {
            long max = 0;
            for (int i = 0; i < str.length(); i++) {
                for (int j = i + 1; j < str.length(); j++) {
                    max = Math.max(max, 10 * (str.charAt(i) - '0') + str.charAt(j) - '0');
                }
            }
            svar += max;
        }
        return svar;
    }
}
