package se.timotej.advent.y2017;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Advent3b {
    public Advent3b() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        new Advent3b().calc();
    }

    int limit = Integer.parseInt(Online.get(3).get(0));
    int x = 0;
    int y = 0;
    Map<Pair<Integer, Integer>, Integer> g = new HashMap<>();

    private void calc() {
        for (int i = 1; true; i += 2) {
            walk(1, 0, i);
            walk(0, 1, i);
            walk(-1, 0, i + 1);
            walk(0, -1, i + 1);
        }
    }

    private void walk(int dx, int dy, int dist) {
        for (int j = 0; j < dist; j++) {
            int tal = getTal(x, y);
            System.out.printf("(%d, %d)\t%d%n", x, y, tal);
            if (tal > limit) System.exit(0);
            g.put(Pair.of(x, y), tal);
            x += dx;
            y += dy;
        }
    }

    private int getTal(int x, int y) {
        if (x == 0 && y == 0) return 1;
        int sum = 0;
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                sum += g.getOrDefault(Pair.of(x + dx, y + dy), 0);
            }
        }
        return sum;
    }
}
