package se.timotej.advent.y2023;

import java.io.IOException;
import java.util.List;

public class Advent21 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent21().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = {0, 1, 0, -1};
    int[] dy = {1, 0, -1, 0};

    private long calc(List<String> strs) {
        long svar = 0;
        char[][] g = Util.charMatrix(strs);
        int maxx = g[0].length;
        int maxy = g.length;
        boolean[][][] p = new boolean[65][maxy][maxx];
        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                if (g[y][x] == 'S') {
                    p[0][y][x] = true;
                }
            }
        }
        for (int turn = 0; turn < 64; turn++) {
            for (int y = 0; y < maxy; y++) {
                for (int x = 0; x < maxx; x++) {
                    if (p[turn][y][x]) {
                        for (int dir = 0; dir < 4; dir++) {
                            int xx = x + dx[dir];
                            int yy = y + dy[dir];
                            if (xx >= 0 && xx < maxx && yy >= 0 && yy < maxy && g[yy][xx] != '#') {
                                p[turn + 1][yy][xx] = true;
                            }
                        }
                    }
                }
            }
        }

        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                if (p[64][y][x]) {
                    svar++;
                    System.out.print('O');
                } else {
                    System.out.print(g[y][x]);
                }
            }
            System.out.println();
        }

        return svar;
    }
}
