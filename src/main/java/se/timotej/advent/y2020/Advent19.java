package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent19 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent19().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    Map<Integer, Apa> g = new HashMap<>();

    private long calc(List<String> strs) {
        long sum = 0;
        List<List<String>> input = Util.splitByDoubleNewline(strs);
        for (String str : input.get(0)) {
            String[] split = str.split(": ");
            int key = Integer.parseInt(split[0]);
            Apa apa;
            if (split[1].startsWith("\"")) {
                apa = new Apa(split[1].charAt(1));
            } else {
                String[] split2 = split[1].split(" \\| ");
                List<List<Integer>> subrules = new ArrayList<>();
                for (String s : split2) {
                    List<Integer> allInts = Util.findAllInts(s);
                    subrules.add(allInts);
                }
                apa = new Apa(subrules);
            }
            g.put(key, apa);
        }

        for (String str : input.get(1)) {
            int matches = evaluate(str, 0, 0);
            if (matches == str.length()) {
                sum++;
            }
        }

        return sum;
    }

    private int evaluate(String str, int key, int startIndex) {
        if (startIndex >= str.length()) {
            return -1;
        }
        Apa apa = g.get(key);
        if (apa.c != 0) {
            if (str.charAt(startIndex) == apa.c) {
                return startIndex + 1;
            } else {
                return -1;
            }
        } else {
            for (List<Integer> subrule : apa.subrules) {
                int index = startIndex;
                for (Integer rule : subrule) {
                    index = evaluate(str, rule, index);
                    if (index == -1) {
                        break;
                    }
                }
                if (index != -1) {
                    return index;
                }
            }
            return -1;
        }
    }

    private static class Apa {
        char c;
        List<List<Integer>> subrules;

        public Apa(char c) {
            this.c = c;
        }

        public Apa(List<List<Integer>> subrules) {
            this.subrules = subrules;
        }
    }
}
