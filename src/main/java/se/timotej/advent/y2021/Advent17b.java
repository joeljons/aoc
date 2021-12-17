package se.timotej.advent.y2021;

import java.io.IOException;
import java.util.List;

public class Advent17b {

    public static void main(String[] args) throws IOException {
        var svar = new Advent17b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long hits = 0;
        for (long yy = -80; yy < 1000; yy++) {
            for (long xx = 0; xx < 320; xx++) {
                long x = 0;
                long y = 0;
                long dx = xx;
                long dy = yy;
                long hogsta = y;
                while (y > -100) {
                    x += dx;
                    y += dy;
                    hogsta = Math.max(y, hogsta);
                    dy--;
                    if (dx > 0) {
                        dx--;
                    } else if (dx < 0) {
                        dx++;
                    }
                    if (x >= 282 && x <= 314 && y >= -80 && y <= -45) {
                        hits++;
                        break;
                    }
                }
            }
        }
        return hits;
    }

}
