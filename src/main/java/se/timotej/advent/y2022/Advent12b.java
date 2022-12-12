package se.timotej.advent.y2022;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class Advent12b {
    public static void main(String[] args) throws IOException {
        int svar = new Advent12b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = {0, 1, 0, -1};
    int[] dy = {1, 0, -1, 0};

    private int calc(List<String> strs) {
        int maxy = strs.size();
        int maxx = strs.get(0).length();
        char[][] g = new char[maxy][];
        int[][] cost = new int[maxy][maxx];
        Pos start = null;
        for (int y = 0; y < maxy; y++) {
            String str = strs.get(y);
            g[y] = str.toCharArray();
            for (int x = 0; x < maxx; x++) {
                if (g[y][x] == 'E') {
                    g[y][x] = 'z';
                    start = new Pos(x, y);
                }
            }
        }
        Queue<Pos> q = new ArrayDeque<>();
        q.add(start);
        while (!q.isEmpty()) {
            Pos now = q.remove();
            for (int dir = 0; dir < 4; dir++) {
                int x = now.x + dx[dir];
                int y = now.y + dy[dir];
                if (x >= 0 && x < maxx && y >= 0 && y < maxy && (int) g[now.y][now.x] - (int) g[y][x] <= 1 && cost[y][x] == 0) {
                    cost[y][x] = cost[now.y][now.x] + 1;
                    q.add(new Pos(x, y));
                }
            }
        }
        int svar = Integer.MAX_VALUE;
        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                if (g[y][x] == 'a' && cost[y][x] != 0) {
                    svar = Math.min(svar, cost[y][x]);
                }
            }
        }
        return svar;
    }


    private static class Pos {
        int x, y;

        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
