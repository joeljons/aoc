package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.List;

public class Advent11 {

    public static void main(String[] args) throws IOException {
        String svar = new Advent11().calc(Online.get());
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
        for (int y = 1; y <= 298; y++) {
            for (int x = 1; x <= 298; x++) {
                int sum = 0;
                for (int dy = 0; dy < 3; dy++) {
                    for (int dx = 0; dx < 3; dx++) {
                        sum += g[y + dy][x + dx];
                    }
                }
                if (sum > best) {
                    best = sum;
                    svar = x + "," + y;
                    System.out.println("best = " + best);
                }
            }
        }

        return svar;
    }
}
