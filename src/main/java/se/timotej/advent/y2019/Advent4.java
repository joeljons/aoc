package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.List;

public class Advent4 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent4().calc(Online.get());
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

    private boolean funkar(int i) {
        boolean adj = false;
        int last = 10;
        while (i > 0) {
            int nu = i % 10;
            i /= 10;
            if (nu > last) {
                return false;
            }
            if (nu == last) {
                adj = true;
            }
            last = nu;
        }
        return adj;
    }
}
