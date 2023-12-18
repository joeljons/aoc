package se.timotej.advent.y2023;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.*;

public class Advent18 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent18().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};

    private long calc(List<String> strs) {
        int x = 0;
        int y = 0;
        Set<Pair<Integer, Integer>> g = new HashSet<>();
        for (String str : strs) {
            String[] split = str.split(" ");
            int dir;
            if (split[0].equals("U")) {
                dir = 0;
            } else if (split[0].equals("R")) {
                dir = 1;
            } else if (split[0].equals("D")) {
                dir = 2;
            } else if (split[0].equals("L")) {
                dir = 3;
            } else {
                throw new RuntimeException();
            }
            int len = Integer.parseInt(split[1]);
            for (int i = 0; i < len; i++) {
                g.add(Pair.of(x, y));
                x += dx[dir];
                y += dy[dir];
            }
            g.add(Pair.of(x, y));
        }
        Queue<Pair<Integer, Integer>> q = new ArrayDeque<>();
        q.add(Pair.of(1, 1));
        while (!q.isEmpty()) {
            Pair<Integer, Integer> now = q.remove();
            x = now.getLeft();
            y = now.getRight();
            for (int dir = 0; dir < 4; dir++) {
                Pair<Integer, Integer> key = Pair.of(x + dx[dir], y + dy[dir]);
                if (!g.contains(key)) {
                    g.add(key);
                    q.add(key);
                }
            }
        }
        return g.size();
    }
}
