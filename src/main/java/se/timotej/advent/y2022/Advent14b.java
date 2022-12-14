package se.timotej.advent.y2022;

import java.io.IOException;
import java.util.List;

public class Advent14b {
    public static void main(String[] args) throws IOException {
        int svar = new Advent14b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int svar = 0;
        char[][] g = new char[600][2048];
        int maxy = 0;
        for (String str : strs) {
            List<Integer> allInts = Util.findAllPositiveInts(str);
            for (int i = 2; i < allInts.size(); i += 2) {
                int x = allInts.get(i - 2) + 512;
                int y = allInts.get(i - 1);
                int tox = allInts.get(i) + 512;
                int toy = allInts.get(i + 1);
                maxy = Math.max(maxy, Math.max(y, toy));
                while (x != tox || y != toy) {
                    g[y][x] = '#';
                    if (x < tox) {
                        x++;
                    }
                    if (x > tox) {
                        x--;
                    }
                    if (y < toy) {
                        y++;
                    }
                    if (y > toy) {
                        y--;
                    }
                }
                g[y][x] = '#';
            }
        }
        maxy += 2;
        for (int xx = 0; xx < 2048; xx++) {
            g[maxy][xx] = '#';
        }
        while (true) {
            boolean atrest;
            int x = 500 + 512;
            int y = 0;
            do {
                atrest = true;
                if (g[y + 1][x] == 0) {
                    y++;
                    atrest = false;
                } else if (g[y + 1][x - 1] == 0) {
                    y++;
                    x--;
                    atrest = false;
                } else if (g[y + 1][x + 1] == 0) {
                    y++;
                    x++;
                    atrest = false;
                } else {
                    g[y][x] = 'o';
                    svar++;
                    if (x == 500 + 512 && y == 0) {
                        return svar;
                    }
                }
            } while (!atrest);
        }
    }
}
