package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.List;

public class Advent15 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent15().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        List<Integer> g = Util.findAllInts(strs.get(0));
        int[] t = new int[3000];
        for (int i = 0; i < g.size(); i++) {
            t[i] = g.get(i);
        }
        for (int i = g.size(); i < 2020; i++) {
            int last = t[i - 1];
            int j = i - 2;
            while (j >= 0 && t[j] != last) {
                j--;
            }
            if (j < 0) {
                t[i] = 0;
            } else {
                t[i] = i - 1 - j;
            }
        }
        return t[2019];
    }

}
