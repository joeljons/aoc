package se.timotej.advent.y2019;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent3 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent3().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        Set<Pair<Integer, Integer>> pos0 = getPosses(strs.get(0));
        Set<Pair<Integer, Integer>> pos1 = getPosses(strs.get(1));
        int best = Integer.MAX_VALUE;
        for (Pair<Integer, Integer> integerIntegerPair : pos1) {
            if (pos0.contains(integerIntegerPair)) {
                int dist = Math.abs(integerIntegerPair.getKey()) + Math.abs(integerIntegerPair.getValue());
                if (dist < best) {
                    best = dist;
                }
            }
        }
        return best;
    }

    private Set<Pair<Integer, Integer>> getPosses(String line) {
        String[] split = line.split(",");
        int x = 0;
        int y = 0;
        HashSet<Pair<Integer, Integer>> pairs = new HashSet<>();
        for (String s : split) {
            char dir = s.charAt(0);
            int walk = Integer.parseInt(s.substring(1));
            for (int i = 0; i < walk; i++) {
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
                pairs.add(Pair.of(x, y));
            }
        }
        return pairs;
    }

}
