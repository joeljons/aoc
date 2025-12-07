package se.timotej.advent.y2025;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

public class Advent5b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent5b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        List<List<String>> input = Util.splitByDoubleNewline(strs);
        NavigableSet<Pair<Long, Long>> g = new TreeSet<>();
        NavigableSet<Long> h = new TreeSet<>();
        for (String range : input.get(0)) {
            List<Long> myRange = Util.findAllPositiveLongs(range);
            Long from = myRange.getFirst();
            Long to = myRange.getLast()+1;
            h.add(from);
            h.add(to);
        }
        for (String range : input.get(0)) {
            List<Long> myRange = Util.findAllPositiveLongs(range);
            Long from = myRange.getFirst();
            Long to = myRange.getLast()+1;
            Long last = from;
            for (Long item : h.subSet(from, false, to, true)) {
                g.add(Pair.of(last, item));
                last = item;
            }
        }
        return g.stream().mapToLong(pair -> pair.getRight() - pair.getLeft()).sum();
    }
}
