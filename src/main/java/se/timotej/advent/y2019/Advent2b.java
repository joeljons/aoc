package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.List;

public class Advent2b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent2b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        for (int noun = 0; noun < 100; noun++) {
            for (int verb = 0; verb < 100; verb++) {
                Integer[] g = Util.findAllInts(strs.get(0)).toArray(new Integer[0]);
                int pos = 0;
                g[1] = noun;
                g[2] = verb;
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
                if (g[0] == 19690720) {
                    return 100 * noun + verb;
                }
            }
        }
        throw new RuntimeException();

    }

}
