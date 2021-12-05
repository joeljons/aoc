package se.timotej.advent.y2021;

import java.io.IOException;
import java.util.List;

public class Advent5b {

    public static void main(String[] args) throws IOException {
        var svar = new Advent5b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int sum = 0;
        int[][] g = new int[1024][1024];
        for (String str : strs) {
            System.out.println("str = " + str);
            List<Integer> allInts = Util.findAllPositiveInts(str);
            int x0 = allInts.get(0);
            int y0 = allInts.get(1);
            int x1 = allInts.get(2);
            int y1 = allInts.get(3);
            if (x0 == x1) {
                for (int y = Math.min(y0, y1); y <= Math.max(y0, y1); y++) {
                    g[y][x0]++;
                }
            } else if (y0 == y1) {
                for (int x = Math.min(x0, x1); x <= Math.max(x0, x1); x++) {
                    g[y0][x]++;
                }
            } else {
                int dx = x1>x0 ? 1 : -1;
                int dy = y1>y0 ? 1 : -1;
                int xx = x0;
                int yy = y0;
                for (int d = 0; d <= Math.abs(x1 - x0); d++) {
                    g[yy][xx]++;
                    xx += dx;
                    yy += dy;
                }
            }
        }
        for (int[] ints : g) {
            for (int anInt : ints) {
                if (anInt > 1) {
                    sum++;
                }
            }
        }
        return sum;
    }

}
