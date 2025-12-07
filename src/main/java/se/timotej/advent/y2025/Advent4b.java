package se.timotej.advent.y2025;

import java.io.IOException;
import java.util.List;

public class Advent4b {

    int[] dx = new int[]{0, 1, 1, 1, 0, -1, -1, -1};
    int[] dy = new int[]{-1, -1, 0, 1, 1, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        long svar = new Advent4b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;
        char[][] g = Util.charMatrix(strs);
        int maxy = g.length;
        int maxx = g[0].length;
        boolean removed;
        do {
            removed = false;
            for (int y = 0; y < maxy; y++) {
                for (int x = 0; x < maxx; x++) {
                    if (g[y][x] == '@') {
                        int rollCount = 0;
                        for (int dir = 0; dir < 8; dir++) {
                            int xx = x + dx[dir];
                            int yy = y + dy[dir];
                            if (xx >= 0 && xx < maxx && yy >= 0 && yy < maxy && g[yy][xx] == '@') {
                                rollCount++;
                            }
                        }
                        if (rollCount < 4) {
                            svar++;
                            g[y][x] = '.';
                            removed = true;
                        }
                    }
                }
            }
        } while (removed);
        return svar;
    }
}
