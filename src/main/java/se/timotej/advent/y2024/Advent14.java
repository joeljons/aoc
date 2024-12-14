package se.timotej.advent.y2024;

import java.io.IOException;
import java.util.List;

public class Advent14 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent14().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        int maxx = 101;
        int maxy = 103;
        int[][] g = new int[maxy][maxx];
        long[][] quads = new long[2][2];
        for (String str : strs) {
            List<Long> line = Util.findAllLongs(str);
            long x = line.get(0);
            long y = line.get(1);
            long dx = (line.get(2) + maxx) % maxx;
            long dy = (line.get(3) + maxy) % maxy;
            int seconds = 100;
            x = (x + seconds * dx) % maxx;
            y = (y + seconds * dy) % maxy;
            g[(int)y][(int)x]++;
            System.out.println("x = " + x);
            System.out.println("y = " + y);
            System.out.println();
            if (x < maxx / 2) {
                if (y < maxy / 2) {
                    quads[0][0]++;
                } else if (y > maxy / 2) {
                    quads[1][0]++;
                }
            } else if (x > maxx / 2) {
                if (y < maxy / 2) {
                    quads[0][1]++;
                } else if (y > maxy / 2) {
                    quads[1][1]++;
                }
            }
        }
        for (int[] ints : g) {
            for (int anInt : ints) {
                System.out.print(anInt > 0 ? anInt : ".");
            }
            System.out.println();
        }

        return quads[0][0] * quads[0][1] * quads[1][0] * quads[1][1];
    }
}
