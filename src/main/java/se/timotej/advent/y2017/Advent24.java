package se.timotej.advent.y2017;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Advent24 {
    public static void main(String[] args) throws IOException {
        //new Advent21().calc(Collections.singletonList("3"));
        new Advent24().calc(Online.get(24));
    }

    private void calc(List<String> strs) {
        List<Pair<Integer, Integer>> g = new ArrayList<>();
        for (String str : strs) {
            String[] split = str.split("/");
            g.add(Pair.of(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
        }
        int[] best = new int[g.size()];
        boolean[] taken = new boolean[g.size()];
        int svar = rek(g, best, taken, 0);
        System.out.println("svar = " + svar);
        Online.submit(24, 1, svar);
    }

    private int rek(List<Pair<Integer, Integer>> g, int[] best, boolean[] taken, int now) {
        int i = 0;
        int bestSvar = 0;
        for (Pair<Integer, Integer> integerIntegerPair : g) {
            if (integerIntegerPair.getKey() == now && !taken[i]) {
                taken[i] = true;
                int svar = integerIntegerPair.getKey() + integerIntegerPair.getValue() + rek(g, best, taken,
                        integerIntegerPair.getValue());
                if (svar > bestSvar) {
                    bestSvar = svar;
                }
                taken[i] = false;
            }
            if (integerIntegerPair.getValue() == now && !taken[i]) {
                taken[i] = true;
                int svar = integerIntegerPair.getKey() + integerIntegerPair.getValue() + rek(g, best, taken,
                        integerIntegerPair.getKey());
                if (svar > bestSvar) {
                    bestSvar = svar;
                }
                taken[i] = false;
            }
            i++;
        }
        return bestSvar;
    }

}