package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.List;

public class Advent10 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent10().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int svar = 0;
        int maxy = strs.size();
        int maxx = strs.get(0).length();
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
                    }
                }
            }
        }

        return svar;
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
