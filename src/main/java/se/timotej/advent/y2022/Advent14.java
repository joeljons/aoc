package se.timotej.advent.y2022;

import java.io.IOException;
import java.util.List;

public class Advent14 {
    public static void main(String[] args) throws IOException {
        int svar = new Advent14().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int svar = 0;
        char[][] g = new char[600][600];
        for (String str : strs) {
            List<Integer> allInts = Util.findAllPositiveInts(str);
            for (int i = 2; i < allInts.size(); i+=2) {
                int x = allInts.get(i - 2);
                int y = allInts.get(i - 1);
                int tox = allInts.get(i);
                int toy = allInts.get(i + 1);
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
        while (true) {
            boolean atrest;
            int x = 500;
            int y = 0;
            do {
                if (y == 599) {
                    return svar;
                }
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
                }
            } while (!atrest);
        }
    }
}
