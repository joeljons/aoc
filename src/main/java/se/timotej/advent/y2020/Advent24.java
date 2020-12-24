package se.timotej.advent.y2020;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent24 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent24().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    Map<Pair<Integer, Integer>, Boolean> g = new HashMap<>();

    private long calc(List<String> strs) {
        long sum = 0;
        for (String str : strs) {
            Pair<Integer, Integer> pos = go(str);
            g.put(pos, !g.getOrDefault(pos, false));
        }
        for (Boolean value : g.values()) {
            if (value) {
                sum++;
            }
        }
        return sum;
    }

    private Pair<Integer, Integer> go(String str) {
        int x = 0;
        int y = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == 'e') {
                x++;
            } else if (c == 'w') {
                x--;
            } else if (c == 'n') {
                char d = str.charAt(++i);
                if (d == 'w') {
                    x--;
                    y++;
                } else if (d == 'e') {
                    y++;
                }
            } else if (c == 's') {
                char d = str.charAt(++i);
                if (d == 'w') {
                    y--;
                } else if (d == 'e') {
                    x++;
                    y--;
                }
            }
        }
        return Pair.of(y, x);
    }
}