package se.timotej.advent.y2019;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent3b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent3b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        Map<Pair<Integer, Integer>, Integer> pos0 = getPosses(strs.get(0));
        Map<Pair<Integer, Integer>, Integer> pos1 = getPosses(strs.get(1));
        int best = Integer.MAX_VALUE;
        for (Pair<Integer, Integer> integerIntegerPair : pos1.keySet()) {
            if (pos0.containsKey(integerIntegerPair)) {
                int dist = pos0.get(integerIntegerPair) + pos1.get(integerIntegerPair);
                if (dist < best) {
                    best = dist;
                }
            }
        }
        return best;
    }

    private Map<Pair<Integer, Integer>, Integer> getPosses(String line) {
        String[] split = line.split(",");
        int x = 0;
        int y = 0;
        int walked = 0;
        Map<Pair<Integer, Integer>, Integer> pairs = new HashMap<>();
        for (String s : split) {
            char dir = s.charAt(0);
            int walk = Integer.parseInt(s.substring(1));
            for (int i = 0; i < walk; i++) {
                walked++;
                if (dir == 'R') {
                    x++;
                }
                if (dir == 'L') {
                    x--;
                }
                if (dir == 'D') {
                    y++;
                }
                if (dir == 'U') {
                    y--;
                }
                pairs.putIfAbsent(Pair.of(x, y), walked);
            }
        }
        return pairs;
    }

}
