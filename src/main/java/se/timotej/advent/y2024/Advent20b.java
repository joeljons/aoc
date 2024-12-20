package se.timotej.advent.y2024;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class Advent20b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent20b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};

    private long calc(List<String> strs) {
        long svar = 0;
        char[][] g = Util.charMatrix(strs);
        int maxy = g.length;
        int maxx = g[0].length;

        int[][] fromStart = null;
        int[][] fromEnd = null;
        int ex = 0;
        int ey = 0;
        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                if (g[y][x] == 'S') {
                    fromStart = fillDist(g, Pair.of(x, y));
                }
                if (g[y][x] == 'E') {
                    fromEnd = fillDist(g, Pair.of(ex = x, ey = y));
                }
            }
        }

        long baseline = fromStart[ey][ex];
        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                if (fromStart[y][x] != -1) {
                    for (int y2 = Math.max(y - 20, 0); y2 < Math.min(y + 21, maxy); y2++) {
                        for (int x2 = Math.max(x - 20, 0); x2 < Math.min(x + 21, maxx); x2++) {
                            if (fromEnd[y2][x2] != -1) {
                                int dist = Math.abs(y2 - y) + Math.abs(x2 - x);
                                if (dist <= 20) {
                                    int thisRun = fromStart[y][x] + fromEnd[y2][x2] + dist;
                                    if (baseline - thisRun >= 100) {
                                        svar++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return svar;
    }

    private int[][] fillDist(char[][] g, Pair<Integer, Integer> start) {
        int maxy = g.length;
        int maxx = g[0].length;
        int[][] dist = new int[maxy][maxx];
        for (int y = 0; y < maxy; y++) {
            Arrays.fill(dist[y], -1);
        }

        Queue<Pair<Integer, Integer>> q = new ArrayDeque<>();
        q.add(start);
        dist[start.getRight()][start.getLeft()] = 0;

        while (!q.isEmpty()) {
            Pair<Integer, Integer> now = q.remove();
            int x = now.getLeft();
            int y = now.getRight();
            for (int dir = 0; dir < 4; dir++) {
                int newX = x + dx[dir];
                int newY = y + dy[dir];
                if (newX >= 0 && newX < maxx && newY >= 0 && newY < maxy && g[newY][newX] != '#' && dist[newY][newX] == -1) {
                    dist[newY][newX] = dist[y][x] + 1;
                    q.add(Pair.of(newX, newY));
                }
            }
        }
        return dist;
    }
}
