package se.timotej.advent.y2021;

import java.io.IOException;
import java.util.List;

public class Advent25 {

    public static void main(String[] args) throws IOException {
        var svar = new Advent25().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int sum = 0;
        int maxy = strs.size();
        int maxx = strs.get(0).length();
        char[][] g = new char[maxy][maxx];
        boolean[][] willMove = new boolean[maxy][maxx];
        for (int y = 0; y < maxy; y++) {
            g[y] = strs.get(y).toCharArray();
        }
        boolean moved;
        do {
            moved = false;
            for (int y = 0; y < maxy; y++) {
                for (int x = 0; x < maxx; x++) {
                    willMove[y][x] = g[y][x] == '>' && g[y][(x + 1) % maxx] == '.';
                }
            }
            for (int y = 0; y < maxy; y++) {
                for (int x = 0; x < maxx; x++) {
                    if (willMove[y][x]) {
                        g[y][x] = '.';
                        g[y][(x + 1) % maxx] = '>';
                        moved = true;
                    }
                }
            }

            for (int y = 0; y < maxy; y++) {
                for (int x = 0; x < maxx; x++) {
                    willMove[y][x] = g[y][x] == 'v' && g[(y + 1) % maxy][x] == '.';
                }
            }
            for (int y = 0; y < maxy; y++) {
                for (int x = 0; x < maxx; x++) {
                    if (willMove[y][x]) {
                        g[y][x] = '.';
                        g[(y + 1) % maxy][x] = 'v';
                        moved = true;
                    }
                }
            }
            sum++;
        } while (moved);
        return sum;
    }
}

