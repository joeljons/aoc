package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.List;

public class Advent11b_v2 {

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        String svar = new Advent11b_v2().calc(Online.get());
        long duration = System.currentTimeMillis() - start;
        System.out.println("duration = " + duration);
        System.out.println("svar = " + svar);
//        Online.submit(svar);
    }

    int[][] g = new int[301][301];


    private String calc(List<String> strs) {
        int in = 3999;
        String svar = "";
        for (int y = 1; y <= 300; y++) {
            for (int x = 1; x <= 300; x++) {
                if (x == 3 && y == 5) {
                    int hej = 42;
                }
                int rackid = x + 10;
                int powerLevel = rackid * y;
                powerLevel += in;
                powerLevel *= rackid;
                powerLevel = (powerLevel / 100) % 10;
                powerLevel -= 5;
                g[y][x] = powerLevel;
            }
        }


        int best = -100000;
        int[][] sums = new int[301][301];
        for (int y = 1; y <= 300; y++) {
            for (int x = 1; x <= 300; x++) {
                sums[y][x] = sums[y - 1][x] + sums[y][x - 1] - sums[y - 1][x - 1] + g[y][x];
            }
        }

        for (int S = 1; S <= 300; S++) {
            for (int x = S; x <= 300; x++) {
                for (int y = S; y <= 300; y++) {
                    int nu = sums[y][x] - sums[y - S][x] - sums[y][x - S] + sums[y - S][x - S];
                    if (nu > best) {
                        best = nu;
                        System.out.println("best = " + best);
                        svar = (x - S) + "," + (y - S) + "," + S;
                    }
                }
            }
        }

        return svar;
    }
}
