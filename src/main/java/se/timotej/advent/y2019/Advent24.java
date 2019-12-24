package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent24 {

    public static void main(String[] args) throws IOException {
        long start = System.nanoTime();
        long svar = new Advent24().calc(Online.get());
        long duration = (System.nanoTime() - start) / 1000000;
        System.out.println("duration = " + duration);
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};

    private long calc(List<String> strs) {
        Set<Long> seen = new HashSet<>();
        char[][][] g = new char[2][5][5];
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                g[0][y][x] = strs.get(y).charAt(x);
            }
        }
        int odd = 0;
        while (true) {
            long bio = getBioDiversity(g[odd]);
            if (seen.contains(bio)) {
                return bio;
            }
            seen.add(bio);
            transform(g[odd], g[(odd + 1) % 2]);
            odd = (odd + 1) % 2;
        }
    }

    private void transform(char[][] from, char[][] to) {
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                int adjCount = 0;
                for (int d = 0; d < 4; d++) {
                    int nyx = x + dx[d];
                    int nyy = y + dy[d];
                    if (nyx >= 0 && nyy >= 0 && nyx < 5 && nyy < 5 && from[nyy][nyx] == '#') {
                        adjCount++;
                    }
                }
                if (from[y][x] == '#') {
                    to[y][x] = adjCount == 1 ? '#' : '.';
                } else {
                    to[y][x] = (adjCount == 1 || adjCount == 2) ? '#' : '.';
                }
            }
        }
    }

    private long getBioDiversity(char[][] chars) {
        long pot = 1;
        long svar = 0;
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                if (chars[y][x] == '#') {
                    svar += pot;
                }
                pot *= 2;
            }
        }
        return svar;
    }
}
