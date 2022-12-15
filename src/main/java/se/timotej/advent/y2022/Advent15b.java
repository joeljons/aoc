package se.timotej.advent.y2022;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Advent15b {
    public static void main(String[] args) throws IOException {
        long svar = new Advent15b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        List<List<Integer>> inputs = new ArrayList<>();
        for (String str : strs) {
            inputs.add(Util.findAllInts(str));
        }
        for (int y = 0; y <= 4000000; y++) {
            Set<Pair<Integer, Integer>> g = new TreeSet<>();
            for (List<Integer> allInts : inputs) {
                int sx = allInts.get(0);
                int sy = allInts.get(1);
                int bx = allInts.get(2);
                int by = allInts.get(3);
                int dist = Math.abs(bx - sx) + Math.abs(by - sy);
                int xdist = dist - Math.abs(y - sy);
                if (xdist >= 0) {
                    g.add(Pair.of(sx - xdist, sx + xdist));
                }
            }
            int hix = 0;
            for (Pair<Integer, Integer> pair : g) {
                if (pair.getLeft() <= hix) {
                    hix = Math.max(hix, pair.getRight());
                } else {
                    return ((long) hix + 1) * 4000000 + (long) y;
                }
                if (hix > 4000000) {
                    break;
                }
            }
        }
        throw new RuntimeException("Answer not found");
    }
}
