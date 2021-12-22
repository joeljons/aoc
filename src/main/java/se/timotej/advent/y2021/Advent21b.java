package se.timotej.advent.y2021;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent21b {

    public static void main(String[] args) throws IOException {
        var svar = new Advent21b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        Pair<Long, Long> wins = rek(9, 6, 0, 0, 0);
        return Math.max(wins.getLeft(), wins.getRight());
    }

    Map<Long, Pair<Long, Long>> g = new HashMap<>();

    private Pair<Long, Long> rek(int pos0, int pos1, int point0, int point1, int turn) {
        long key = ((((pos0 * 100L) + pos1) * 100 + point0) * 1000 + point1) * 1000 + turn;
        if (g.containsKey(key)) {
            return g.get(key);
        }
        if (point0 >= 21) {
            return Pair.of(1L, 0L);
        } else if (point1 >= 21) {
            return Pair.of(0L, 1L);
        }
        Pair<Long, Long> wins = Pair.of(0L, 0L);
        for (int d0 = 1; d0 <= 3; d0++) {
            for (int d1 = 1; d1 <= 3; d1++) {
                for (int d2 = 1; d2 <= 3; d2++) {
                    int d = d0 + d1 + d2;
                    Pair<Long, Long> rekResult;
                    if (turn == 0) {
                        int pos = (pos0 + d) % 10;
                        int point = point0 + pos + 1;
                        rekResult = rek(pos, pos1, point, point1, 1);
                    } else {
                        int pos = (pos1 + d) % 10;
                        int point = point1 + pos + 1;
                        rekResult = rek(pos0, pos, point0, point, 0);
                    }
                    wins = Pair.of(rekResult.getLeft() + wins.getLeft(), rekResult.getRight() + wins.getRight());
                }
            }
        }
        g.put(key, wins);
        return wins;
    }

}
