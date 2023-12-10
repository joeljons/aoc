package se.timotej.advent.y2023;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class Advent10 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent10().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};

    private long calc(List<String> strs) {
        long svar = 0;
        Queue<Pair<Integer, Integer>> q = new ArrayDeque<>();
        int maxx = strs.get(0).length();
        int maxy = strs.size();
        char[][] g = new char[maxy][maxx];
        int[][] dist = new int[maxy][maxx];
        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                char c = strs.get(y).charAt(x);
                g[y][x] = c;
                if (c == 'S') {
                    q.add(Pair.of(x, y));
                    g[y][x] = '-'; //Adapted for my input :)

                }
            }
        }
        while (!q.isEmpty()) {
            Pair<Integer, Integer> now = q.remove();
            int x = now.getLeft();
            int y = now.getRight();
            List<Integer> dirs = null;
            if (g[y][x] == '|') {
                dirs = List.of(0, 2);
            }
            if (g[y][x] == '-') {
                dirs = List.of(1, 3);
            }
            if (g[y][x] == 'L') {
                dirs = List.of(0, 1);
            }
            if (g[y][x] == 'J') {
                dirs = List.of(0, 3);
            }
            if (g[y][x] == '7') {
                dirs = List.of(2, 3);
            }
            if (g[y][x] == 'F') {
                dirs = List.of(1, 2);
            }
            for (Integer dir : dirs) {
                int nx = x + dx[dir];
                int ny = y + dy[dir];
                if (dist[ny][nx] == 0) {
                    dist[ny][nx] = dist[y][x] + 1;
                    svar = Math.max(svar, dist[ny][nx]);
                    q.add(Pair.of(nx, ny));
                }
            }
        }
        return svar;
    }

}
