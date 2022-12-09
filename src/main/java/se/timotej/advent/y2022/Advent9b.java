package se.timotej.advent.y2022;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent9b {
    public static void main(String[] args) throws IOException {
        int svar = new Advent9b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        Set<String> pos = new HashSet<>();
        Pos[] rope = new Pos[10];
        for (int p = 0; p < 10; p++) {
            rope[p] = new Pos();
        }
        for (String str : strs) {
            char dir = str.charAt(0);
            Integer len = Util.findAllInts(str).get(0);
            int dx = 0;
            int dy = 0;
            if (dir == 'U') {
                dy = -1;
            }
            if (dir == 'D') {
                dy = 1;
            }
            if (dir == 'L') {
                dx = -1;
            }
            if (dir == 'R') {
                dx = 1;
            }
            System.out.println(str);
            for (int i = 0; i < len; i++) {
                rope[0].x += dx;
                rope[0].y += dy;
                for (int p = 1; p < 10; p++) {
                    if (Math.abs(rope[p].x - rope[p - 1].x) > 1 || Math.abs(rope[p].y - rope[p - 1].y) > 1) {
                        if (rope[p].x - rope[p - 1].x >= 1) {
                            rope[p].x--;
                        }
                        if (rope[p - 1].x - rope[p].x >= 1) {
                            rope[p].x++;
                        }
                        if (rope[p].y - rope[p - 1].y >= 1) {
                            rope[p].y--;
                        }
                        if (rope[p - 1].y - rope[p].y >= 1) {
                            rope[p].y++;
                        }
                    }
                }
                pos.add(rope[9].x + "_" + rope[9].y);
            }
        }
        return pos.size();
    }

    private static class Pos {
        int x, y;
    }
}
