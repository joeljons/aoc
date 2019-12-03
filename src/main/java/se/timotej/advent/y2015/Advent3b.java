package se.timotej.advent.y2015;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent3b {
    public static void main(String[] args) throws IOException {
        new Advent3b().calc(Online.get(3));
    }

    private void calc(List<String> strs) {
        Set<Pair<Integer, Integer>> g = new HashSet<>();
        int[] x = new int[]{0, 0};
        int[] y = new int[]{0, 0};
        g.add(Pair.of(0, 0));
        String str = strs.get(0);
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '<') {
                x[i%2]--;
            } else if (c == '>') {
                x[i%2]++;
            } else if (c == 'v') {
                y[i%2]--;
            } else if (c == '^') {
                y[i%2]++;
            }
            g.add(Pair.of(x[i%2], y[i%2]));
        }
        System.out.println("g.size() = " + g.size());
    }

}
