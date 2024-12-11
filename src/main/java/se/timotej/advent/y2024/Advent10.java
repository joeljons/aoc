package se.timotej.advent.y2024;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent10 {

    private char[][] g;
    private int maxy;
    private int maxx;

    public static void main(String[] args) throws IOException {
        long svar = new Advent10().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;

        g = Util.charMatrix(strs);
        maxy = g.length;
        maxx = g[0].length;
        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                svar += calcScore(x, y);
            }
        }

        return svar;
    }

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};

    private long calcScore(int x, int y) {
        if (g[y][x] != '0') {
            return 0;
        }
        Set<Pair<Integer, Integer>> q = new HashSet<>();
        q.add(Pair.of(x, y));
        for (char height = '1'; height <= '9'; height++) {
            Set<Pair<Integer, Integer>> q2 = new HashSet<>();
            for (Pair<Integer, Integer> now : q) {
                for (int dir = 0; dir < 4; dir++) {
                    int x2 = now.getLeft() + dx[dir];
                    int y2 = now.getRight() + dy[dir];
                    if (x2 >= 0 && x2 < maxx && y2 >= 0 && y2 < maxx && g[y2][x2] == height) {
                        q2.add(Pair.of(x2, y2));
                    }
                }
            }
            q = q2;
        }
        return q.size();
    }
}
