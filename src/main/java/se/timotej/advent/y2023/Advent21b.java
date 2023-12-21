package se.timotej.advent.y2023;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.math.IntMath.mod;

public class Advent21b {

    private static final int LEN = 26501365;

    public static void main(String[] args) throws IOException {
        long svar = new Advent21b().calc(Online.get(21));
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = {0, 1, 0, -1};
    int[] dy = {1, 0, -1, 0};

    private long calc(List<String> strs) {
        char[][] g = Util.charMatrix(strs);
        if (g[0].length != g.length) {
            throw new RuntimeException();
        }
        int gs = g[0].length;
        int mid = gs / 2;
        if (g[mid][mid] != 'S') {
            throw new RuntimeException();
        }

        Set<Pair<Integer, Integer>> p = new HashSet<>();
        p.add(Pair.of(mid, mid));
        long c = -1;
        long x1 = -1;
        long x2 = -1;
        for (int turn = 0; turn <= 65 + 2 * 131; turn++) {
            Set<Pair<Integer, Integer>> p2 = new HashSet<>();
            for (Pair<Integer, Integer> now : p) {
                Integer x = now.getLeft();
                Integer y = now.getRight();
                for (int dir = 0; dir < 4; dir++) {
                    int xx = x + dx[dir];
                    int yy = y + dy[dir];
                    if (g[mod(yy, gs)][mod(xx, gs)] != '#') {
                        p2.add(Pair.of(xx, yy));
                    }
                }
            }
            if (turn == 65) {
                c = p.size();
            } else if (turn == 65 + 131) {
                x1 = p.size();
            } else if (turn == 65 + 2 * 131) {
                x2 = p.size();
            }

            if (turn % 131 == 65) {
                System.out.println("p.size() = " + p.size());
            }
            p = p2;
        }
        long a = (x2 - 2 * x1 + c) / 2;
        long b = x1 - c - a;
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("c = " + c);
        long x = LEN / 131;
        return a * x * x + b * x + c;
    }
}
