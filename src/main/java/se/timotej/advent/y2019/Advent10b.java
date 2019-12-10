package se.timotej.advent.y2019;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Advent10b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent10b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    boolean[][] g;

    private int calc(List<String> strs) {
        int svar = 0;
        int maxy = strs.size();
        int maxx = strs.get(0).length();
        g = new boolean[maxy][maxx];
        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                g[y][x] = ast(strs, x, y);
            }
        }
        int besty = -1;
        int bestx = -1;
        for (int starty = 0; starty < maxy; starty++) {
            for (int startx = 0; startx < maxx; startx++) {
                if (ast(strs, startx, starty)) {
                    int nuser = 0;
                    for (int dy = -maxy; dy <= maxy; dy++) {
                        for (int dx = -maxx; dx <= maxx; dx++) {
                            int seeSomething = 0;
                            if ((dx == 0 && Math.abs(dy) == 1) || (dy == 0 && Math.abs(dx) == 1) || gcd(Math.abs(dx), Math.abs(dy)) == 1) {
                                int x = startx + dx;
                                int y = starty + dy;
                                for (; 0 <= y && y < maxy && 0 <= x && x < maxx; y += dy, x += dx) {
                                    if (ast(strs, x, y)) {
                                        seeSomething = 1;
                                    }
                                }
                            }
                            nuser += seeSomething;
                        }
                    }
                    if (nuser > svar) {
                        svar = nuser;
                        besty = starty;
                        bestx = startx;
                    }
                }
            }
        }
        System.out.println("bestx = " + bestx);
        System.out.println("besty = " + besty);

        List<Pair<Integer, Integer>> dirs = new ArrayList<>();

        for (int dy = -maxy; dy <= maxy; dy++) {
            for (int dx = -maxx; dx <= maxx; dx++) {
                int seeSomething = 0;
                if ((dx == 0 && Math.abs(dy) == 1) || (dy == 0 && Math.abs(dx) == 1) || gcd(Math.abs(dx), Math.abs(dy)) == 1) {
                    dirs.add(Pair.of(dy, dx));
                }
            }
        }
        dirs.sort((a, b) -> {
            double radA = getRad(a);
            double radB = getRad(b);
            return Double.compare(radA, radB);
        });

        int killCount = 0;
        while (true) {
            for (Pair<Integer, Integer> dir : dirs) {
                int dy = dir.getLeft();
                int dx = dir.getRight();

                int x = bestx + dx;
                int y = besty + dy;
                for (; 0 <= y && y < maxy && 0 <= x && x < maxx; y += dy, x += dx) {
                    if (g[y][x]) {
                        killCount++;
                        g[y][x] = false;
                        if (killCount == 200) {
                            return x * 100 + y;
                        }
                        break;
                    }
                }
            }
        }
    }

    private double getRad(Pair<Integer, Integer> a) {
        double v = Math.atan2(a.getLeft(), a.getRight());
        if (v <= 0) {
            v += Math.PI * 2;
        }
        v += Math.PI / 2;
        if (v >= Math.PI * 2) {
            v -= Math.PI * 2;
        }
        return v;
    }

    private int gcd(int n1, int n2) {
        if (n1 == 0 || n2 == 0) {
            return 42;
        }
        while (n1 != n2) {
            if (n1 > n2) {
                n1 -= n2;
            } else {
                n2 -= n1;
            }
        }
        return n1;
    }

    private boolean ast(List<String> strs, int x, int y) {
        return strs.get(y).charAt(x) == '#';
    }
}
