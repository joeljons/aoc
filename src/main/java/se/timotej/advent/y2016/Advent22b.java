package se.timotej.advent.y2016;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutionException;

public class Advent22b {

    public static void main(String[] args) throws IOException, ExecutionException {
        int svar = new Advent22b().calc(Online.get());
        System.out.println("svar = " + svar);
        //Online.submit(String.valueOf(svar));
    }

    int[] dx = {0, 1, 0, -1};
    int[] dy = {1, 0, -1, 0};

    private int calc(List<String> strs) {
        char[][] g = new char[25][37];
        int[][] dist = new int[25][37];
        Queue<Point> q = new ArrayDeque<>();
        for (String str : strs.subList(2, strs.size())) {
            String[] line = str.split("[xy\\- T]+");
            int x = Integer.parseInt(line[1]);
            int y = Integer.parseInt(line[2]);
            int use = Integer.parseInt(line[4]);
            int avail = Integer.parseInt(line[5]);
            if (use == 0) {
                g[y][x] = '_';
                q.add(new Point(x, y));
            } else if (use <= 93) {
                g[y][x] = '.';
            } else {
                g[y][x] = '#';
            }
        }
        for (char[] chars : g) {
            System.out.println(new String(chars));
        }
        while (!q.isEmpty()) {
            Point nu = q.remove();
            for (int dir = 0; dir < 4; dir++) {
                int nyy = nu.y + dy[dir];
                int nyx = nu.x + dx[dir];
                if (nyy >= 0 && nyy < 25 && nyx >= 0 && nyx < 37 && g[nyy][nyx] == '.' && dist[nyy][nyx] == 0) {
                    dist[nyy][nyx] = dist[nu.y][nu.x] + 1;
                    q.add(new Point(nyx, nyy));
                }
            }
        }
        System.out.println("dist[0][36] = " + dist[0][36]);

        int svar = dist[0][36] + 5 * 35;
        return svar;
    }

    private class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}