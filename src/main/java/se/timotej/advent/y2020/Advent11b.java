package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.List;

public class Advent11b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent11b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int maxy = strs.size();
        char[][] g = new char[maxy][];
        for (int i = 0; i < maxy; i++) {
            g[i] = strs.get(i).toCharArray();
        }
        int maxx = g[0].length;
        boolean changed;
        do {
            changed = false;
            char[][] g2 = new char[maxy][maxx];
            for (int y = 0; y < maxy; y++) {
                for (int x = 0; x < maxx; x++) {
                    g2[y][x] = g[y][x];
                    int occupied = 0;
                    for (int dy = -1; dy <= 1; dy++) {
                        for (int dx = -1; dx <= 1; dx++) {
                            if (dy == 0 && dx == 0) {
                                continue;
                            }
                            int yy = y + dy;
                            int xx = x + dx;
                            while (0 <= yy && yy < maxy && 0 <= xx && xx < maxx && g[yy][xx] == '.') {
                                yy += dy;
                                xx += dx;
                            }
                            if (0 <= yy && yy < maxy && 0 <= xx && xx < maxx) {
                                if (g[yy][xx] == '#') {
                                    occupied++;
                                }
                            }
                        }
                    }
                    if (g[y][x] == 'L' && occupied == 0) {
                        g2[y][x] = '#';
                        changed = true;
                    } else if (g[y][x] == '#' && occupied >= 5) {
                        g2[y][x] = 'L';
                        changed = true;
                    }
                }
            }
            g = g2;
        } while (changed);
        int sum = 0;

        for (char[] chars : g) {
            for (char aChar : chars) {
                if (aChar == '#') {
                    sum++;
                }
            }
        }
        return sum;
    }
}
