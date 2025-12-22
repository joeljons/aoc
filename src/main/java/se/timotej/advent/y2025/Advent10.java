package se.timotej.advent.y2025;

import java.io.IOException;
import java.util.List;

public class Advent10 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent10().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;
        for (String str : strs) {
            String[] machine = str.split(" ");
            String m = cut(machine[0]);

            int target = 0;
            for (int i = 0; i < m.length(); i++) {
                if (m.charAt(i) == '#') {
                    target += 1 << i;
                }
            }

            int[] buttons = new int[machine.length - 2];
            for (int buttonI = 1; buttonI < machine.length - 1; buttonI++) {
                for (String light : cut(machine[buttonI]).split(",")) {
                    buttons[buttonI - 1] += 1 << Integer.parseInt(light);
                }
            }

            int bestClickCount = Integer.MAX_VALUE;
            for (int i = 0; i < (1 << buttons.length); i++) {
                int now = 0;
                for (int j = 0; j < buttons.length; j++) {
                    if ((i & (1 << j)) != 0) {
                        now ^= buttons[j];
                    }
                }
                if (now == target) {
                    bestClickCount = Math.min(bestClickCount, Integer.bitCount(i));
                }
            }
            if (bestClickCount == Integer.MAX_VALUE) {
                throw new RuntimeException();
            }
            svar += bestClickCount;
        }

        return svar;
    }

    private String cut(String s) {
        return s.substring(1, s.length() - 1);
    }
}
