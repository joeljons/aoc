package se.timotej.advent.y2024;

import java.io.IOException;
import java.util.List;

public class Advent6b {

    private char[][] g;
    private int maxy;
    private int maxx;

    public static void main(String[] args) throws IOException {
        int svar = new Advent6b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};

    private int calc(List<String> strs) {
        int svar = 0;
        g = Util.charMatrix(strs);
        maxy = g.length;
        maxx = g[0].length;
        int sy = -1;
        int sx = -1;
        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                if (g[y][x] == '^') {
                    sy = y;
                    sx = x;
                }
            }
        }

        for (int blockY = 0; blockY < maxy; blockY++) {
            for (int blockX = 0; blockX < maxx; blockX++) {
                if (g[blockY][blockX] == '.') {
                    g[blockY][blockX] = '#';
                    if (wouldLoop(sy, sx)) {
                        svar++;
                    }
                    g[blockY][blockX] = '.';
                }
            }
        }

        return svar;
    }

    private boolean wouldLoop(int y, int x) {
        int dir = 0;
        int count = 0;
        while (y >= 0 && y < maxy && x >= 0 && x < maxx) {
            if (count++ > maxx * maxy) {
                return true;
            }
            int nyy = y + dy[dir];
            int nyx = x + dx[dir];
            if (nyy >= 0 && nyy < maxy && nyx >= 0 && nyx < maxx && g[nyy][nyx] == '#') {
                dir = (dir + 1) % 4;
            } else {
                y = nyy;
                x = nyx;
            }
        }
        return false;
    }
}
