package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.List;

public class Advent22 {

    public static void main(String[] args) throws IOException {
        String svar = new Advent22().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private String calc(List<String> strs) {
        int targetx = 15;
        int targety = 740;
        int depth = 3558;

//        depth = 510;Advent22
//        targetx = targety  = 10;
        long svar = 0;

        long[][] g = new long[targety + 1][targetx + 1];
        for (int y = 0; y <= targety; y++) {
            for (int x = 0; x <= targetx; x++) {
                long gi;
                if (x == 0) {
                    gi = y * 48271;
                } else if (y == 0) {
                    gi = x * 16807;
                } else if (y == targety && x == targetx) {
                    gi = 0;
                } else {
                    gi = g[y][x - 1] * g[y - 1][x];
                }

               gi %= 20183;

                long el = (gi + depth) % 20183;
                g[y][x] = el;
                svar += el % 3;
                System.out.print(el%3 == 0 ? "." : el%3 == 1 ? "=":"|");
            }
            System.out.println();
        }


        return String.valueOf(svar);
    }
}

//fel 11896
//fel 770