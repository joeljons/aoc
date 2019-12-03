package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.List;

public class Advent11b {

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        String svar = new Advent11b().calc(Online.get());
        long duration = (System.currentTimeMillis()-start)/1000;
        System.out.println("duration = " + duration);
        System.out.println("svar = " + svar);
//        Online.submit(svar);
    }

    long[][] g = new long[301][301];


    private String calc(List<String> strs) {
        int in = 3999;
        String svar = "";
        for (int y = 1; y <= 300; y++) {
            for (int x = 1; x <= 300; x++) {
                if (x == 3 && y == 5) {
                    int hej = 42;
                }
                long rackid = x + 10;
                long powerLevel = rackid * y;
                powerLevel += in;
                powerLevel *= rackid;
                powerLevel = (powerLevel / 100) % 10;
                powerLevel -= 5;
                g[y][x] = powerLevel;
            }
        }

        int best = -100000;
        for (int y = 1; y <= 300; y++) {
            System.out.println("y = " + y);
            for (int x = 1; x <= 300; x++) {
                for (int S = 1; S < 300; S++) {
                    if (x + S - 1 <= 300 && y + S - 1 <= 300) {
                        int sum = 0;
                        for (int dy = 0; dy < S; dy++) {
                            for (int dx = 0; dx < S; dx++) {
                                sum += g[y + dy][x + dx];
                            }
                        }
                        if (sum > best) {
                            best = sum;
                            svar = x + "," + y + "," + S;
                            System.out.println("best = " + best);
                        }
                    }
                }
            }
        }

        return svar;
    }
}
