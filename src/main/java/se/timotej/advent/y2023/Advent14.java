package se.timotej.advent.y2023;

import java.io.IOException;
import java.util.List;

public class Advent14 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent14().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;
        char[][] g = Util.charMatrix(strs);
        int maxx = g[0].length;
        int maxy = g.length;
        for (int x = 0; x < maxx; x++) {
            for (int y = 1; y < maxy; y++) {
                while (y > 0 && g[y][x] == 'O' && g[y - 1][x] == '.') {
                    g[y - 1][x] = 'O';
                    g[y][x] = '.';
                    y--;
                }
            }
        }
        for (int x = 0; x < maxx; x++) {
            for (int y = 0; y < maxy; y++) {
                if (g[y][x] == 'O') {
                    svar += maxy - y;
                }
            }
        }
        return svar;
    }
}
