package se.timotej.advent.y2022;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Advent20 {
    public static void main(String[] args) throws IOException {
        int svar = new Advent20().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int[] ints = Util.intArray(strs);
        List<Num> g = new ArrayList<>();
        int len = ints.length;
        for (int i = 0; i < len; i++) {
            g.add(new Num(ints[i], i));
        }
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                Num me = g.get(j);
                if (me.moveIndex == i) {
                    g.remove(me);
                    g.add((j + me.n + 10 * (len - 1)) % (len - 1), me);
                    break;
                }
            }
        }
        for (int i = 0; i < len; i++) {
            if (g.get(i).n == 0) {
                return g.get((i + 1000) % len).n
                        + g.get((i + 2000) % len).n
                        + g.get((i + 3000) % len).n;
            }
        }
        return 0;
    }

    private static class Num {
        int n;
        int moveIndex;

        public Num(int n, int moveIndex) {
            this.n = n;
            this.moveIndex = moveIndex;
        }
    }
}
