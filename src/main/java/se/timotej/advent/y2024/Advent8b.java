package se.timotej.advent.y2024;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent8b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent8b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        int maxy = strs.size();
        int maxx = strs.get(0).length();
        Multimap<Character, Pair<Integer, Integer>> g = HashMultimap.create();
        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                if (strs.get(y).charAt(x) != '.') {
                    g.put(strs.get(y).charAt(x), Pair.of(x, y));
                }
            }
        }

        Set<Pair<Integer, Integer>> antinodes = new HashSet<>();
        for (Character antenna : g.keySet()) {
            for (Pair<Integer, Integer> a : g.get(antenna)) {
                for (Pair<Integer, Integer> b : g.get(antenna)) {
                    if (a != b) {
                        int dx = a.getLeft() - b.getLeft();
                        int dy = a.getRight() - b.getRight();
                        int x = a.getLeft();
                        int y = a.getRight();
                        while (x >= 0 && y >= 0 && x < maxx && y < maxy) {
                            antinodes.add(Pair.of(x, y));
                            x += dx;
                            y += dy;
                        }
                    }
                }
            }
        }

        return antinodes.size();
    }
}
