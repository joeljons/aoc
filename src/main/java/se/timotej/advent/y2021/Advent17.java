package se.timotej.advent.y2021;

import java.io.IOException;
import java.util.List;

public class Advent17 {

    public static void main(String[] args) throws IOException {
        var svar = new Advent17().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long bestY = -100;
        for (long yy = -80; yy < 1000; yy++) {
            for (long xx = 0; xx < 320; xx++) {
                long x = 0;
                long y = 0;
                long dx = xx;
                long dy = yy;
                boolean hit = false;
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
                        hit = true;
                        break;
                    }
                }
                if (hit) {
                    bestY = Math.max(bestY, hogsta);
                }
            }
        }
        return bestY;
    }

}
