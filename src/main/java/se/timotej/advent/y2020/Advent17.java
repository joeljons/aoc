package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.List;

public class Advent17 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent17().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long sum = 0;
        char[][][][] g = new char[2][30][30][30];
        {
            int y = 0;
            for (String str : strs) {
                for (int x = 0; x < str.length(); x++) {
                    char c = str.charAt(x);
                    g[0][10][y+10][x+10] = c;
                }
                y++;
            }
        }
        char[][][] next = null;
        char[][][] now;
        for (int cycle = 0; cycle < 6; cycle++) {
            now = g[cycle % 2];
            next = g[(cycle + 1) % 2];
            for (int z = 0; z < 30; z++) {
                for (int y = 0; y < 30; y++) {
                    for (int x = 0; x < 30; x++) {
                        int count = grannCount(now, x, y, z);
                        char val = '.';
                        if (now[z][y][x] == '#' && (count == 2 || count == 3)) {
                            val = '#';
                        } else if (now[z][y][x] != '#' && count == 3) {
                            val = '#';
                        }
                        next[z][y][x] = val;
                    }
                }
            }
        }
        for (int z = 0; z < 30; z++) {
            for (int y = 0; y < 30; y++) {
                for (int x = 0; x < 30; x++) {
                    if (next[z][y][x] == '#') {
                        sum++;
                    }
                }
            }
        }
        return sum;
    }

    private int grannCount(char[][][] now, int x, int y, int z) {
        int count = 0;
        for (int dz = -1; dz <= 1; dz++) {
            int nyz = z + dz;
            if (nyz < 0 || nyz >= 30) {
                continue;
            }
            for (int dy = -1; dy <= 1; dy++) {
                int nyy = y + dy;
                if (nyy < 0 || nyy >= 30) {
                    continue;
                }
                for (int dx = -1; dx <= 1; dx++) {
                    int nyx = x + dx;
                    if (nyx < 0 || nyx >= 30) {
                        continue;
                    }
                    if (dz == 0 && dy == 0 && dx == 0) {
                        continue;
                    }
                    if (now[nyz][nyy][nyx] == '#') {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
