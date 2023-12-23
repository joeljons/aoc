package se.timotej.advent.y2023;

import java.io.IOException;
import java.util.List;

public class Advent23b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent23b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};

    char[][] g;
    int maxx;
    int maxy;
    boolean[][] seen;


    private long calc(List<String> strs) {
        g = Util.charMatrix(strs);
        maxx = g[0].length;
        maxy = g.length;
        seen = new boolean[maxy][maxx];

        return rek(1, 0, 0);
    }

    int best = 0;

    private int rek(int x, int y, int len) {
        if (y == maxy - 1) {
            if (len > best) {
                best = len;
                System.out.println("len = " + len);
            }
            return len;
        }

        seen[y][x] = true;
        int longest = 0;
        for (int dir = 0; dir < 4; dir++) {
            int xx = x + dx[dir];
            int yy = y + dy[dir];
            if (xx > 0 && xx < maxx && yy > 0 && yy < maxy && g[yy][xx] != '#' && !seen[yy][xx]) {
                longest = Math.max(longest, rek(xx, yy, len + 1));
            }
        }
        seen[y][x] = false;
        return longest;
    }
}
