package se.timotej.advent.y2024;

import java.io.IOException;
import java.util.List;

public class Advent6 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent6().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};

    private int calc(List<String> strs) {
        int svar = 0;
        char[][] g = Util.charMatrix(strs);
        int maxy = g.length;
        int maxx = g[0].length;
        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                if (g[y][x] == '^') {
                    int dir = 0;
                    while (y >= 0 && y < maxy && x >= 0 && x < maxx) {
                        g[y][x] = 'X';
                        int nyy = y + dy[dir];
                        int nyx = x + dx[dir];
                        if (nyy >= 0 && nyy < maxy && nyx >= 0 && nyx < maxx && g[nyy][nyx] == '#') {
                            dir = (dir + 1) % 4;
                        } else {
                            y = nyy;
                            x = nyx;
                        }
                    }
                    break;
                }
            }
        }

        for (char[] chars : g) {
            for (char aChar : chars) {
                if (aChar == 'X') {
                    svar++;
                }
            }
        }

        return svar;
    }
}
