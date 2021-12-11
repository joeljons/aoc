package se.timotej.advent.y2021;

import java.io.IOException;
import java.util.List;

public class Advent11 {

    public static void main(String[] args) throws IOException {
        var svar = new Advent11().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int sum = 0;
        char[][] g = new char[10][];
        for (int y = 0; y < 10; y++) {
            g[y] = strs.get(y).toCharArray();
        }
        for (int step = 0; step < 100; step++) {
            boolean[][] flashedNu = new boolean[10][10];
            for (int y = 0; y < 10; y++) {
                for (int x = 0; x < 10; x++) {
                    g[y][x]++;
                }
            }
            boolean flashed;
            do {
                flashed = false;
                for (int y = 0; y < 10; y++) {
                    for (int x = 0; x < 10; x++) {
                        if (g[y][x] > '9' && !flashedNu[y][x]) {
                            sum++;
                            flashed = true;
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
        }
        return sum;
    }
}
