package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent24b {

    public static final int LEVELS = 500;

    public static void main(String[] args) throws IOException {
        long start = System.nanoTime();
        long svar = new Advent24b().calc(Online.get());
        long duration = (System.nanoTime() - start) / 1000000;
        System.out.println("duration = " + duration);
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};

    private long calc(List<String> strs) {
        Set<Long> seen = new HashSet<>();
        char[][][][] g = new char[2][LEVELS][5][5];
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                g[0][LEVELS / 2][y][x] = strs.get(y).charAt(x);
            }
        }
        int odd = 0;
        for (int minute = 0; minute < 200; minute++) {
            transform(g[odd], g[(odd + 1) % 2]);
            odd = (odd + 1) % 2;
        }
        long svar = 0;
        for (int level = 0; level < LEVELS; level++) {
            svar += getBugCount(g[odd][level]);
        }
        return svar;
    }

    private void transform(char[][][] from, char[][][] to) {
        for (int level = 1; level < LEVELS - 1; level++) {
            for (int y = 0; y < 5; y++) {
                for (int x = 0; x < 5; x++) {
                    if (x == 2 && y == 2) {
                        to[level][y][x] = '?';
                        continue;
                    }
                    int adjCount = 0;
                    for (int d = 0; d < 4; d++) {
                        int nyx = x + dx[d];
                        int nyy = y + dy[d];
                        if (nyx >= 0 && nyy >= 0 && nyx < 5 && nyy < 5 && from[level][nyy][nyx] == '#') {
                            adjCount++;
                        }
                    }
                    if (y == 0) {
                        if (from[level - 1][1][2] == '#') {
                            adjCount++;
                        }
                    }
                    if (y == 4) {
                        if (from[level - 1][3][2] == '#') {
                            adjCount++;
                        }
                    }
                    if (x == 0) {
                        if (from[level - 1][2][1] == '#') {
                            adjCount++;
                        }
                    }
                    if (x == 4) {
                        if (from[level - 1][2][3] == '#') {
                            adjCount++;
                        }
                    }
                    if (y == 2 && x == 1) {
                        for (int yy = 0; yy < 5; yy++) {
                            if (from[level + 1][yy][0] == '#') {
                                adjCount++;
                            }
                        }
                    }
                    if (y == 2 && x == 3) {
                        for (int yy = 0; yy < 5; yy++) {
                            if (from[level + 1][yy][4] == '#') {
                                adjCount++;
                            }
                        }
                    }
                    if (y == 1 && x == 2) {
                        for (int xx = 0; xx < 5; xx++) {
                            if (from[level + 1][0][xx] == '#') {
                                adjCount++;
                            }
                        }
                    }
                    if (y == 3 && x == 2) {
                        for (int xx = 0; xx < 5; xx++) {
                            if (from[level + 1][4][xx] == '#') {
                                adjCount++;
                            }
                        }
                    }
                    if (from[level][y][x] == '#') {
                        to[level][y][x] = adjCount == 1 ? '#' : '.';
                    } else {
                        to[level][y][x] = (adjCount == 1 || adjCount == 2) ? '#' : '.';
                    }
                }
            }
        }
    }

    private long getBugCount(char[][] chars) {
        long pot = 1;
        long svar = 0;
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                if (chars[y][x] == '#') {
                    svar += pot;
                }
            }
        }
        return svar;
    }
}
