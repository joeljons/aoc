package se.timotej.advent.y2024;

import java.io.IOException;
import java.util.List;

public class Advent13b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent13b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;

        for (List<String> strings : Util.splitByDoubleNewline(strs)) {
            List<Long> buttonA = Util.findAllLongs(strings.get(0));
            List<Long> buttonB = Util.findAllLongs(strings.get(1));
            List<Long> prize = Util.findAllLongs(strings.get(2));
            prize = List.of(prize.get(0) + 10000000000000L, prize.get(1) + 10000000000000L);
            long a = (prize.get(0) * buttonB.get(1) - prize.get(1) * buttonB.get(0)) / (buttonA.get(0) * buttonB.get(1) - buttonA.get(1) * buttonB.get(0));
            long b = (prize.get(0) - buttonA.get(0) * a) / buttonB.get(0);

            if (buttonA.get(0) * a + buttonB.get(0) * b == prize.get(0) && buttonA.get(1) * a + buttonB.get(1) * b == prize.get(1)) {
                svar += 3 * a + b;
            }
        }

        return svar;
    }
}

/*
a * aX + b * bX = pX
a * aY + b * bY = pY

(a * aX + b * bX)*bY = pX*bY
(a * aY + b * bY)*bX = pY*bX

a*(aX*bY-aY*bX) = pX*bY - pY*bX

a= (pX*bY - pY*bX) / (aX*bY - aY*bX)
*/