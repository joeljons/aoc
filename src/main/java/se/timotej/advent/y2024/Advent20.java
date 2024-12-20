package se.timotej.advent.y2024;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class Advent20 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent20().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};

    private long calc(List<String> strs) {
        long svar = 0;
        char[][] g = Util.charMatrix(strs);

        long baseline = doRun(g);

        int maxy = g.length;
        int maxx = g[0].length;

        for (int y = 1; y < maxy - 1; y++) {
            for (int x = 1; x < maxx - 1; x++) {
                if (g[y][x] == '#') {
                    g[y][x] = '.';
                    long thisRun = doRun(g);
                    long spar = baseline - thisRun;
                    if (spar >= 100) {
                        svar++;
                    }
                    g[y][x] = '#';
                }
            }
        }

        return svar;
    }

    private long doRun(char[][] g) {
        int maxy = g.length;
        int maxx = g[0].length;
        int[][] dist = new int[maxy][maxx];

        Queue<Pair<Integer, Integer>> q = new ArrayDeque<>();
        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                if (g[y][x] == 'S') {
                    q.add(Pair.of(x, y));
                }
            }
        }

        while (!q.isEmpty()) {
            Pair<Integer, Integer> now = q.remove();
            int x = now.getLeft();
            int y = now.getRight();
            if (g[y][x] == 'E') {
                return dist[y][x];
            }
            for (int dir = 0; dir < 4; dir++) {
                int newX = x + dx[dir];
                int newY = y + dy[dir];
                if (newX >= 0 && newX <= maxx && newY >= 0 && newY <= maxy && g[newY][newX] != '#' && dist[newY][newX] == 0) {
                    dist[newY][newX] = dist[y][x] + 1;
                    q.add(Pair.of(newX, newY));
                }
            }
        }
        throw new RuntimeException();
    }
}
