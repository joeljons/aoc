package se.timotej.advent.y2023;

import org.apache.commons.lang3.tuple.Triple;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class Advent16 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent16().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};

    private long calc(List<String> strs) {
        char[][] g = Util.charMatrix(strs);
        int maxx = g[0].length;
        int maxy = g.length;
        return countEnergized(maxy, maxx,  Triple.of(0, 0, 1), g);
    }

    private long countEnergized(int maxy, int maxx, Triple<Integer, Integer, Integer> start, char[][] g) {
        long svar = 0;
        boolean[][][] taken = new boolean[maxy][maxx][4];
        Queue<Triple<Integer, Integer, Integer>> q = new ArrayDeque<>();
        q.add(start);
        while (!q.isEmpty()) {
            Triple<Integer, Integer, Integer> now = q.remove();
            int x = now.getLeft();
            int y = now.getMiddle();
            int dir = now.getRight();
            if (x < 0 || x >= maxx || y < 0 || y >= maxy || taken[x][y][dir]) {
                continue;
            }
            taken[x][y][dir] = true;
            char c = g[y][x];
            if (c == '.') {
                q.add(go(x, y, dir));
            } else if (c == '|') {
                if (dx[dir] == 0) {
                    q.add(go(x, y, dir));
                } else {
                    q.add(go(x, y, 0));
                    q.add(go(x, y, 2));
                }
            } else if (c == '-') {
                if (dy[dir] == 0) {
                    q.add(go(x, y, dir));
                } else {
                    q.add(go(x, y, 3));
                    q.add(go(x, y, 1));
                }
            } else if (c == '/') {
                q.add(go(x, y, dir ^ 1));
            } else if (c == '\\') {
                q.add(go(x, y, dir ^ 3));
            } else {
                throw new RuntimeException();
            }
        }
        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                for (int dir = 0; dir < 4; dir++) {
                    if (taken[y][x][dir]) {
                        svar++;
                        break;
                    }
                }
            }
        }
        return svar;
    }

    private Triple<Integer, Integer, Integer> go(int x, int y, int dir) {
        return Triple.of(x + dx[dir], y + dy[dir], dir);
    }
}
