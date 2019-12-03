package se.timotej.advent.y2016;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutionException;

public class Advent24b {

    public static void main(String[] args) throws IOException, ExecutionException {
        int svar = new Advent24b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(String.valueOf(svar));
    }

    int[] dx = {0, 1, 0, -1};
    int[] dy = {1, 0, -1, 0};

    private int calc(List<String> strs) {
        int maxy = strs.size();
        int maxx = strs.get(0).length();
        char[][] g = new char[maxy][];
        Point[] points = new Point[8];
        for (int y = 0; y < maxy; y++) {
            g[y] = strs.get(y).toCharArray();
            for (int x = 0; x < maxx; x++) {
                if (g[y][x] >= '0' && g[y][x] < '8') {
                    points[g[y][x] - '0'] = new Point(x, y);
                }
            }
        }
        for (char[] chars : g) {
            System.out.println(new String(chars));
        }
        int[][] dist = new int[8][8];
        for (int from = 0; from < 8; from++) {
            int[][] d = new int[maxy][maxx];
            Queue<Point> q = new ArrayDeque<>();
            q.add(points[from]);
            while (!q.isEmpty()) {
                Point nu = q.remove();
                if (g[nu.y][nu.x] >= '0' && g[nu.y][nu.x] < '8') {
                    dist[from][g[nu.y][nu.x] - '0'] = d[nu.y][nu.x];
                }
                for (int dir = 0; dir < 4; dir++) {
                    int nyy = nu.y + dy[dir];
                    int nyx = nu.x + dx[dir];
                    if (g[nyy][nyx] != '#' && d[nyy][nyx] == 0) {
                        d[nyy][nyx] = d[nu.y][nu.x] + 1;
                        q.add(new Point(nyx, nyy));
                    }
                }
            }
        }
        Integer[] order = new Integer[]{1, 2, 3, 4, 5, 6, 7};
        int best = Integer.MAX_VALUE;
        do {
            int wayDist = dist[0][order[0]];
            for (int i = 1; i < order.length; i++) {
                wayDist += dist[order[i - 1]][order[i]];
            }
            wayDist += dist[order[order.length - 1]][0];
            if (wayDist < best) {
                best = wayDist;
            }
        } while (Util.nextPermutation(order));
        return best;
    }

    private class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}