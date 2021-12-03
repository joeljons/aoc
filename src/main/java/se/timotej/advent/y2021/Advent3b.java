package se.timotej.advent.y2021;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Advent3b {

    public static void main(String[] args) throws IOException {
        var svar = new Advent3b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int oxygen = getNumber(strs, true);
        int co2 = getNumber(strs, false);
        return oxygen * co2;
    }

    private int getNumber(List<String> strs, boolean wantCommon) {
        boolean[] taken = new boolean[strs.size()];
        int len = strs.get(0).length();

        for (int i = 0; i < len; i++) {
            int noll = 0;
            int ett = 0;
            for (int j = 0; j < taken.length; j++) {
                if (taken[j]) {
                    continue;
                }
                String str = strs.get(j);
                if (str.charAt(i) == '1') {
                    ett++;
                } else {
                    noll++;
                }
            }
            String last = null;
            int antal = 0;
            char mostCommon = ett >= noll ? '1' : '0';
            for (int j = 0; j < taken.length; j++) {
                if (wantCommon ^ mostCommon == strs.get(j).charAt(i)) {
                    taken[j] = true;
                }
                if (!taken[j]) {
                    last = strs.get(j);
                    antal++;
                }
            }
            if (antal == 1) {
                return Integer.parseInt(last, 2);
            }
        }
        return 0;
    }
}
