package se.timotej.advent.y2025;

import java.io.IOException;
import java.util.List;

public class Advent7 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent7().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;
        char[][] g = Util.charMatrix(strs);
        int maxy = g.length;
        int maxx = g[0].length;
        for (int y = 0; y < maxy - 1; y++) {
            for (int x = 0; x < maxx - 1; x++) {
                if (g[y][x] == 'S' || g[y][x] == '|') {
                    if (g[y + 1][x] == '^') {
                        svar++;
                        g[y + 1][x - 1] = '|';
                        g[y + 1][x + 1] = '|';
                    } else {
                        g[y + 1][x] = '|';
                    }
                }
            }
        }
        return svar;
    }
}
