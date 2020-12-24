package se.timotej.advent.y2020;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent24b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent24b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }


    private long calc(List<String> strs) {
        Map<Pair<Integer, Integer>, Boolean> g = new HashMap<>();
        for (String str : strs) {
            Pair<Integer, Integer> pos = go(str);
            g.put(pos, !g.getOrDefault(pos, false));
        }
        for (int i = 0; i < 100; i++) {
            System.out.println("i = " + i);
            g = flip(g);
        }
        long sum = 0;
        for (Boolean value : g.values()) {
            if (value) {
                sum++;
            }
        }
        return sum;
    }

    int dx[] = {1, -1, -1, 0, 0, 1};
    int dy[] = {0, 0, 1, 1, -1, -1};

    private Map<Pair<Integer, Integer>, Boolean> flip(Map<Pair<Integer, Integer>, Boolean> from) {
        Map<Pair<Integer, Integer>, Boolean> to = new HashMap<>();
        for (int y = -150; y <= 150; y++) {
            for (int x = -150; x <= 150; x++) {
                final Boolean now = from.getOrDefault(Pair.of(y, x), false);
                int adjacent = 0;
                for (int dir = 0; dir < dx.length; dir++) {
                    int xx = x + dx[dir];
                    int yy = y + dy[dir];
                    if (from.getOrDefault(Pair.of(yy, xx), false)) {
                        adjacent++;
                    }
                }
                if (now) {
                    if (adjacent == 0 || adjacent > 2) {
                        to.put(Pair.of(y, x), false);
                    } else {
                        to.put(Pair.of(y, x), true);
                    }
                } else {
                    if (adjacent == 2) {
                        to.put(Pair.of(y, x), true);
                    } else {
                        to.put(Pair.of(y, x), false);
                    }
                }
            }
        }
        return to;
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