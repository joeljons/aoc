package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.List;

public class Advent2 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent2().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        Integer[] g = Util.findAllInts(strs.get(0)).toArray(new Integer[0]);
        int pos = 0;
        g[1] = 12;
        g[2] = 2;
        while (g[pos] != 99) {
            if (g[pos] == 1) {
                g[g[pos + 3]] = g[g[pos + 1]] + g[g[pos + 2]];
            } else if (g[pos] == 2) {
                g[g[pos + 3]] = g[g[pos + 1]] * g[g[pos + 2]];
            } else {
                throw new RuntimeException();
            }
            pos += 4;
        }
        return g[0];
    }

}
