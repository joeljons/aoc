package se.timotej.advent.y2021;

import java.io.IOException;
import java.util.List;

public class Advent11b {

    public static void main(String[] args) throws IOException {
        var svar = new Advent11b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        char[][] g = new char[10][];
        for (int y = 0; y < 10; y++) {
            g[y] = strs.get(y).toCharArray();
        }
        for (int step = 0; true; step++) {
            boolean[][] flashedNu = new boolean[10][10];
            for (int y = 0; y < 10; y++) {
                for (int x = 0; x < 10; x++) {
                    g[y][x]++;
                }
            }
            boolean flashed;
            int nuFlashes = 0;
            do {
                flashed = false;
                for (int y = 0; y < 10; y++) {
                    for (int x = 0; x < 10; x++) {
                        if (g[y][x] > '9' && !flashedNu[y][x]) {
                            flashed = true;
                            nuFlashes++;
                            flashedNu[y][x] = true;
                            for (int dy = -1; dy <= 1; dy++) {
                                for (int dx = -1; dx <= 1; dx++) {
                                    if (dy == 0 && dx == 0) {
                                        continue;
                                    }
                                    int yy = y + dy;
                                    int xx = x + dx;
                                    if (xx < 0 || xx >= 10 || yy < 0 || yy >= 10) {
                                        continue;
                                    }
                                    g[yy][xx]++;
                                }
                            }

                        }
                    }
                }
            } while (flashed);
            for (int y = 0; y < 10; y++) {
                for (int x = 0; x < 10; x++) {
                    if (flashedNu[y][x]) {
                        g[y][x] = '0';
                    }
                }
            }
            if (nuFlashes == 100) {
                return step + 1;
            }
        }
    }
}
