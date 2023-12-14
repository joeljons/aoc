package se.timotej.advent.y2023;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent14b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent14b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        char[][] g = Util.charMatrix(strs);
        int maxx = g[0].length;
        int maxy = g.length;
        Map<List<Pair<Integer, Integer>>, Integer> loop = new HashMap<>();
        for (int cycle = 0; cycle < 1000000000; cycle++) {
            for (int x = 0; x < maxx; x++) {
                for (int y = 0; y < maxy; y++) {
                    while (y > 0 && g[y][x] == 'O' && g[y - 1][x] == '.') {
                        g[y - 1][x] = 'O';
                        g[y][x] = '.';
                        y--;
                    }
                }
            }
            for (int y = 0; y < maxy; y++) {
                for (int x = 0; x < maxx; x++) {
                    while (x > 0 && g[y][x] == 'O' && g[y][x - 1] == '.') {
                        g[y][x - 1] = 'O';
                        g[y][x] = '.';
                        x--;
                    }
                }
            }
            for (int x = 0; x < maxx; x++) {
                for (int y = maxy - 1; y >= 0; y--) {
                    while (y < maxy - 1 && g[y][x] == 'O' && g[y + 1][x] == '.') {
                        g[y + 1][x] = 'O';
                        g[y][x] = '.';
                        y++;
                    }
                }
            }
            for (int y = 0; y < maxy; y++) {
                for (int x = maxx - 1; x >= 0; x--) {
                    while (x < maxx - 1 && g[y][x] == 'O' && g[y][x + 1] == '.') {
                        g[y][x + 1] = 'O';
                        g[y][x] = '.';
                        x++;
                    }
                }
            }
            if (loop != null) {
                List<Pair<Integer, Integer>> key = new ArrayList<>();
                for (int x = 0; x < maxx; x++) {
                    for (int y = 0; y < maxy; y++) {
                        if (g[y][x] == 'O') {
                            key.add(Pair.of(x, y));
                        }
                    }
                }
                if (loop.containsKey(key)) {
                    int diff = cycle - loop.get(key);
                    cycle += (1000000000 - cycle) / diff * diff;
                    loop = null;
                } else {
                    loop.put(key, cycle);
                }
            }
        }
        long svar = 0;
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
