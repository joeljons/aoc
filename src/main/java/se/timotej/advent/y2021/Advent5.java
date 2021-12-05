package se.timotej.advent.y2021;

import java.io.IOException;
import java.util.List;

public class Advent5 {

    public static void main(String[] args) throws IOException {
        var svar = new Advent5().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int sum = 0;
        int[][] g = new int[1024][1024];
        for (String str : strs) {
            List<Integer> allInts = Util.findAllPositiveInts(str);
            int x0 = allInts.get(0);
            int y0 = allInts.get(1);
            int x1 = allInts.get(2);
            int y1 = allInts.get(3);
            if (x0 == x1) {
                for (int y = Math.min(y0, y1); y <= Math.max(y0, y1); y++) {
                    g[y][x0]++;
                }
            }
            if (y0 == y1) {
                for (int x = Math.min(x0, x1); x <= Math.max(x0, x1); x++) {
                    g[y0][x]++;
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
