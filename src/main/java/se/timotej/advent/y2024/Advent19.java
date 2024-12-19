package se.timotej.advent.y2024;

import java.io.IOException;
import java.util.List;

public class Advent19 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent19().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;

        String[] patterns = strs.getFirst().split(", ");
        for (String s : strs.subList(2, strs.size())) {
            boolean[] possible = new boolean[patterns.length + 50];
            possible[0] = true;
            for (int start = 0; start < s.length(); start++) {
                if (possible[start]) {
                    String substring = s.substring(start);
                    for (String pattern : patterns) {
                        if (substring.startsWith(pattern)) {
                            possible[start + pattern.length()] = true;
                        }
                    }
                }
            }
            if (possible[s.length()]) {
                svar++;
            }
        }

        return svar;
    }
}