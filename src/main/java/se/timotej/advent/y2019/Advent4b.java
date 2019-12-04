package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.List;

public class Advent4b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent4b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        String[] split = strs.get(0).split("-");
        int from = Integer.parseInt(split[0]);
        int to = Integer.parseInt(split[1]);
        int count = 0;
        for (int i = from; i <= to; i++) {
            if (funkar(i)) {
                count++;
            }
        }
        return count;
    }

    private boolean funkar(int starti) {
        int i = starti;
        boolean adj = false;
        int last = 10;
        int last2 = 11;
        boolean adjNu = false;
        while (i > 0) {
            int nu = i % 10;
            i /= 10;
            if (nu > last) {
                return false;
            }
            if (nu == last) {
                adjNu = true;
                if (last == last2) {
                    adjNu = false;
                }
            } else {
                adj |= adjNu;
            }
            last2 = last;
            last = nu;
        }
        return adj || adjNu;
    }
}
