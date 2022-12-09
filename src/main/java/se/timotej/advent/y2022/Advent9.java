package se.timotej.advent.y2022;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent9 {
    public static void main(String[] args) throws IOException {
        int svar = new Advent9().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        Set<String> pos = new HashSet<>();
        int hx = 0;
        int hy = 0;
        int tx = 0;
        int ty = 0;
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
            System.out.println();
            System.out.println(str);
            for (int i = 0; i < len; i++) {
                hx += dx;
                hy += dy;
                if (Math.abs(hx - tx) > 1 || Math.abs(hy - ty) > 1) {
                    if (hx - tx >= 1) {
                        tx++;
                    }
                    if (tx - hx >= 1) {
                        tx--;
                    }
                    if (hy - ty >= 1) {
                        ty++;
                    }
                    if (ty - hy >= 1) {
                        ty--;
                    }
                }
                pos.add(tx + "_" + ty);
            }
        }
        return pos.size();
    }
}
