package se.timotej.advent.y2023;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class Advent10b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent10b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};

    private long calc(List<String> strs) {
        long svar = 0;
        Queue<Pair<Integer, Integer>> q = new ArrayDeque<>();
        int maxx = strs.get(0).length() + 1;
        int maxy = strs.size();
        char[][] g = new char[maxy * 2][maxx * 2];
        int[][] dist = new int[maxy * 2][maxx * 2];
        for (int y = 0; y < maxy; y++) {
            for (int x = 1; x < maxx; x++) {
                char c = strs.get(y).charAt(x - 1);
                g[y * 2][x * 2] = c;
                if (c == 'S') {
                    q.add(Pair.of(x * 2, y * 2));
                    g[y * 2][x * 2] = '-'; //Adapted for my input :)
                    dist[y * 2][x * 2] = 1;
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
                int nx = x + 2 * dx[dir];
                int ny = y + 2 * dy[dir];
                dist[y + dy[dir]][x + dx[dir]] = dist[y][x] + 1;
                g[y + dy[dir]][x + dx[dir]] = 'X';
                if (dist[ny][nx] == 0) {
                    dist[ny][nx] = dist[y][x] + 2;
                    q.add(Pair.of(nx, ny));
                }
            }
        }

        for (int y = 0; y < maxy * 2; y++) {
            for (int x = 0; x < maxx * 2; x++) {
                if (dist[y][x] == 0) {
                    g[y][x] = '.';
                }
            }
        }

        q.add(Pair.of(0, 0));
        while (!q.isEmpty()) {
            Pair<Integer, Integer> now = q.remove();
            int x = now.getLeft();
            int y = now.getRight();
            g[y][x] = ' ';
            for (int dir = 0; dir < 4; dir++) {
                int nx = x + dx[dir];
                int ny = y + dy[dir];
                if (nx >= 0 && nx < 2 * maxx && ny >= 0 && ny < 2 * maxy && (g[ny][nx] == '.' || g[ny][nx] == 0)) {
                    g[ny][nx] = ' ';
                    q.add(Pair.of(nx, ny));
                }
            }
        }

        for (int y = 0; y < maxy * 2; y += 2) {
            for (int x = 0; x < maxx * 2; x += 2) {
                if (g[y][x] == '.') {
                    svar++;
                }
            }
        }
        return svar;
    }

}
