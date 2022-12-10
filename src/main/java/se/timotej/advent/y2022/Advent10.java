package se.timotej.advent.y2022;

import java.io.IOException;
import java.util.List;

public class Advent10 {
    public static void main(String[] args) throws IOException {
        long svar = new Advent10().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    long svar = 0;

    private long calc(List<String> strs) {
        long x = 1;
        int cycle = 1;
        for (String str : strs) {
            checkSignal(cycle, x);
            if (str.equals("noop")) {
                cycle++;
            } else {
                Integer value = Util.findAllInts(str).get(0);
                cycle++;
                checkSignal(cycle, x);
                cycle++;
                x += value;
            }
        }
        return svar;
    }

    private void checkSignal(int cycle, long x) {
        if (cycle == 20 || cycle == 60 || cycle == 100 || cycle == 140 || cycle == 180 || cycle == 220) {
            svar += cycle * x;
        }
    }
}
