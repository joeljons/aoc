package se.timotej.advent.y2024;

import java.io.IOException;
import java.util.List;

public class Advent13 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent13().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;

        for (List<String> strings : Util.splitByDoubleNewline(strs)) {
            List<Integer> buttonA = Util.findAllInts(strings.get(0));
            List<Integer> buttonB = Util.findAllInts(strings.get(1));
            List<Integer> prize = Util.findAllInts(strings.get(2));
            int bestCost = Integer.MAX_VALUE;
            for (int a = 0; a <= 100; a++) {
                for (int b = 0; b <= 100; b++) {
                    if (buttonA.get(0) * a + buttonB.get(0) * b == prize.get(0) && buttonA.get(1) * a + buttonB.get(1) * b == prize.get(1)) {
                        if (3 * a + b < bestCost) {
                            bestCost = 3 * a + b;
                        }
                    }
                }
            }
            if (bestCost != Integer.MAX_VALUE) {
                svar += bestCost;
            }
        }

        return svar;
    }
}
