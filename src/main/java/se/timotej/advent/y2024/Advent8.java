package se.timotej.advent.y2024;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent8 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent8().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;

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
                        antinodes.add(Pair.of(2 * a.getLeft() - b.getLeft(), 2 * a.getRight() - b.getRight()));
                    }
                }
            }
        }
        for (Pair<Integer, Integer> antinode : antinodes) {
            if(antinode.getLeft()>=0 && antinode.getRight()>=0 && antinode.getLeft()<maxx && antinode.getRight()<maxy) {
                svar++;
            }
        }

        return svar;
    }
}
