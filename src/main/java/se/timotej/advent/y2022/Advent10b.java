package se.timotej.advent.y2022;

import java.io.IOException;
import java.util.List;

public class Advent10b {
    public static void main(String[] args) throws IOException {
        new Advent10b().calc(Online.get());
    }

    char[][] svar = {
            "........................................".toCharArray(),
            "........................................".toCharArray(),
            "........................................".toCharArray(),
            "........................................".toCharArray(),
            "........................................".toCharArray(),
            "........................................".toCharArray(),
    };

    private void calc(List<String> strs) {
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

        for (char[] chars : svar) {
            System.out.println(new String(chars));
        }
    }

    private void checkSignal(int cycle, long x) {
        int nowx = ((cycle - 1) % 40);
        int nowy = ((cycle - 1) / 40);
        if (cycle <= 240 && Math.abs(nowx - x) <= 1) {
            svar[nowy][nowx] = '#';
        }
    }
}
