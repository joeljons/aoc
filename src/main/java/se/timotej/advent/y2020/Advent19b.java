package se.timotej.advent.y2020;

import org.apache.commons.lang3.tuple.Triple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Advent19b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent19b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    Map<Integer, Apa> g = new HashMap<>();

    private long calc(List<String> strs) {
        long sum = 0;
        List<List<String>> input = Util.splitByDoubleNewline(strs);
        for (String str : input.get(0)) {
            str = str.replace("8: 42", "8: 42 | 42 8");
            str = str.replace("11: 42 31", "11: 42 31 | 42 11 31");
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
            cache.clear();
            Set<Integer> matches = evaluate(str, 0, 0);
            if (matches.contains(str.length())) {
                sum++;
            }
        }

        return sum;
    }

    Map<Triple<String, Integer, Integer>, Set<Integer>> cache = new HashMap<>();

    private Set<Integer> evaluate(String str, int key, int startIndex) {
        if (startIndex >= str.length()) {
            return Set.of();
        }
        Apa apa = g.get(key);
        if (apa.c != 0) {
            if (str.charAt(startIndex) == apa.c) {
                return Set.of(startIndex + 1);
            } else {
                return Set.of();
            }
        } else {
            Triple<String, Integer, Integer> triple = Triple.of(str, key, startIndex);
            Set<Integer> cachedValue = cache.get(triple);
            if (cachedValue != null) {
                return cachedValue;
            }
            Set<Integer> allPossible = new HashSet<>();
            for (List<Integer> subrule : apa.subrules) {
                Set<Integer> nextIndex = Set.of(startIndex);
                for (Integer rule : subrule) {
                    Set<Integer> nuIndex = nextIndex;
                    nextIndex = new HashSet<>();
                    for (Integer index : nuIndex) {
                        Set<Integer> indexes = evaluate(str, rule, index);
                        nextIndex.addAll(indexes);
                    }
                }
                allPossible.addAll(nextIndex);
            }
            cache.put(triple, allPossible);
            return allPossible;
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
