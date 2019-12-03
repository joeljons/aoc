package se.timotej.advent.y2015;

import java.io.IOException;
import java.util.List;

public class Advent18 {
    public static void main(String[] args) throws IOException {
        new Advent18().calc(Online.get(18));
    }

    char[][][] g;
    int maxx, maxy;

    private void calc(List<String> str) {
        maxy = str.size();
        maxx = str.get(0).length();
        g = new char[2][maxy][maxx];

        for (int y = 0; y < maxy; y++) {
            String row = str.get(y);
            for (int x = 0; x < maxx; x++) {
                g[0][y][x] = row.charAt(x);
            }
        }

        for (int it = 0; it < 100; it++) {
            int from = it % 2;
            int to = (it + 1) % 2;
            for (int y = 0; y < maxy; y++) {
                for (int x = 0; x < maxx; x++) {
                    int litLights = 0;
                    for (int dy = -1; dy <= 1; dy++) {
                        for (int dx = -1; dx <= 1; dx++) {
                            if (dy != 0 || dx != 0) {
                                int ny = y + dy;
                                int nx = x + dx;
                                if (ny >= 0 && ny < maxy && nx >= 0 && nx < maxx && g[from][ny][nx] == '#') {
                                    litLights++;
                                }
                            }
                        }
                    }
                    if (litLights == 3 || (g[from][y][x] == '#' && litLights == 2)) {
                        g[to][y][x] = '#';
                    } else {
                        g[to][y][x] = '.';
                    }
                }
            }
//            debug(g[to]);
        }
        int totLit = 0;
        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                if (g[0][y][x] == '#') {
                    totLit++;
                }
            }
        }
        System.out.println("totLit = " + totLit);
    }

    private void debug(char[][] chars) {
        for (char[] row : chars) {
            System.out.println(new String(row));
        }
        System.out.println();
    }


}
