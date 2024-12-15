package se.timotej.advent.y2024;

import java.io.IOException;
import java.util.List;

public class Advent15 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent15().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;

        List<List<String>> input = Util.splitByDoubleNewline(strs);
        char[][] g = Util.charMatrix(input.get(0));
        int maxy = g.length;
        int maxx = g[0].length;
        int sx = -1;
        int sy = -1;
        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                if (g[y][x] == '@') {
                    sx = x;
                    sy = y;
                    g[y][x] = '.';
                }
            }
        }
        for (String line : input.get(1)) {
            for (char c : line.toCharArray()) {
                int dx = 0;
                int dy = 0;
                if (c == '<') {
                    dx = -1;
                } else if (c == '>') {
                    dx = 1;
                } else if (c == '^') {
                    dy = -1;
                } else if (c == 'v') {
                    dy = 1;
                }
                int nx = sx + dx;
                int ny = sy + dy;
                if (g[ny][nx] == '.') {
                    sx = nx;
                    sy = ny;
                } else if (g[ny][nx] == 'O') {
                    int ox = nx;
                    int oy = ny;
                    while (g[oy][ox] == 'O') {
                        ox += dx;
                        oy += dy;
                    }
                    if (g[oy][ox] == '.') {
                        g[oy][ox] = 'O';
                        g[ny][nx] = '.';
                        sx = nx;
                        sy = ny;
                    }
                } else if(g[ny][nx] != '#'){
                    throw new RuntimeException();
                }
            }
        }

        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                if (g[y][x] == 'O') {
                    svar += 100 * y + x;
                }
            }
        }

        return svar;
    }
}
