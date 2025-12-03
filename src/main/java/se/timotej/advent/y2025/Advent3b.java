package se.timotej.advent.y2025;

import java.io.IOException;
import java.util.List;

public class Advent3b {

    public static final int BATTERY_COUNT = 12;

    public static void main(String[] args) throws IOException {
        long svar = new Advent3b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;
        for (String str : strs) {
            long[][] g = new long[BATTERY_COUNT][str.length()];
            for (int y = 0; y < BATTERY_COUNT; y++) {
                for (int x = str.length() - y - 1; x >= 0; x--) {
                    long now = str.charAt(x) - '0';
                    if (y > 0) {
                        now = (long) (now * Math.pow(10, y)) + g[y - 1][x + 1];
                    }
                    g[y][x] = Math.max(now, x < str.length() - y - 1 ? g[y][x + 1] : 0);
                }
            }
            long jolt = g[BATTERY_COUNT - 1][0];
            svar += jolt;
        }
        return svar;
    }
}
