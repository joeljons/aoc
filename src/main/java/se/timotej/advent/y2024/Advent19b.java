package se.timotej.advent.y2024;

import java.io.IOException;
import java.util.List;

public class Advent19b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent19b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;

        String[] patterns = strs.getFirst().split(", ");
        for (String s : strs.subList(2, strs.size())) {
            long[] possible = new long[patterns.length + 50];
            possible[0] = 1;
            for (int start = 0; start < s.length(); start++) {
                if (possible[start]>0) {
                    String substring = s.substring(start);
                    for (String pattern : patterns) {
                        if (substring.startsWith(pattern)) {
                            possible[start + pattern.length()] += possible[start];
                        }
                    }
                }
            }
            svar += possible[s.length()];
        }

        return svar;
    }
}