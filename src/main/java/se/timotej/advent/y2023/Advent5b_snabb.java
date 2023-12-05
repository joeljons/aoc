package se.timotej.advent.y2023;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Advent5b_snabb {

    public static void main(String[] args) throws IOException {
        long svar = new Advent5b_snabb().calc(Online.get(5));
        System.out.println("svar = " + svar);
        //Online.submit(svar);
    }

    private long calc(List<String> strs) {
        List<List<String>> input = Util.splitByDoubleNewline(strs);

        List<Long> allSeeds = Util.findAllLongs(input.get(0).get(0));

        List<List<List<Long>>> allMaps = new ArrayList<>();
        for (List<String> mapping : input.subList(1, input.size())) {
            List<List<Long>> map = new ArrayList<>();
            for (String line : mapping.subList(1, mapping.size())) {
                List<Long> m = Util.findAllLongs(line);
                m.set(2, m.get(1) + m.get(2) - 1);
                map.add(m);
            }
            allMaps.add(map);
        }

        List<Pair<Long, Long>> g = new ArrayList<>();
        for (int i = 0; i < allSeeds.size() / 2; i++) {
            g.add(Pair.of(allSeeds.get(2 * i), allSeeds.get(2 * i) + allSeeds.get(2 * i + 1) - 1));
        }

        for (List<List<Long>> mapping : allMaps) {
            List<Pair<Long, Long>> beforeSplit;
            do {
                beforeSplit = g;
                g = new ArrayList<>();
                for (Pair<Long, Long> from : beforeSplit) {
                    for (List<Long> m : mapping) {
                        if (from.getLeft() < m.get(1) && m.get(1) < from.getRight()) {
                            g.add(Pair.of(from.getLeft(), m.get(1) - 1));
                            g.add(Pair.of(m.get(1), from.getRight()));
                            from = null;
                            break;
                        } else if (from.getLeft() < m.get(2) && m.get(2) < from.getRight()) {
                            g.add(Pair.of(from.getLeft(), m.get(2) - 1));
                            g.add(Pair.of(m.get(2), from.getRight()));
                            from = null;
                            break;
                        }
                    }
                    if (from != null) {
                        g.add(from);
                    }
                }
            } while (beforeSplit.size() != g.size());
            List<Pair<Long, Long>> next = new ArrayList<>();
            for (Pair<Long, Long> now : g) {
                for (List<Long> m : mapping) {
                    if (m.get(1) <= now.getLeft() && now.getRight() <= m.get(2)) {
                        next.add(Pair.of(now.getLeft() - m.get(1) + m.get(0), now.getRight() - m.get(1) + m.get(0)));
                        now = null;
                        break;
                    }
                }
                if (now != null) {
                    next.add(now);
                }
            }
            g = next;
        }
        return g.stream().mapToLong(Pair::getLeft).min().getAsLong();
    }
}
