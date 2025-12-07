package se.timotej.advent.y2025;

import java.io.IOException;
import java.util.List;

public class Advent7b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent7b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;
        char[][] g = Util.charMatrix(strs);
        int maxy = g.length;
        int maxx = g[0].length;
        long[][] timelines = new long[maxy][maxx];
        for (int y = 0; y < maxy - 1; y++) {
            for (int x = 0; x < maxx; x++) {
                if (g[y][x] == 'S' || g[y][x] == '|') {
                    if (g[y][x] == 'S') {
                        timelines[y][x] = 1;
                    }
                    if (g[y + 1][x] == '^') {
                        g[y + 1][x - 1] = '|';
                        timelines[y + 1][x - 1] += timelines[y][x];
                        g[y + 1][x + 1] = '|';
                        timelines[y + 1][x + 1] += timelines[y][x];
                    } else {
                        g[y + 1][x] = '|';
                        timelines[y + 1][x] += timelines[y][x];
                    }
                }
            }
        }
        for (int x = 0; x < maxx; x++) {
            svar += timelines[maxy - 1][x];
        }
        return svar;
    }
}
