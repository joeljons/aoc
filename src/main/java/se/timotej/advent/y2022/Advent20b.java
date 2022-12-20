package se.timotej.advent.y2022;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Advent20b {
    public static void main(String[] args) throws IOException {
        long svar = new Advent20b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        int[] ints = Util.intArray(strs);
        List<Num> g = new ArrayList<>();
        int len = ints.length;
        for (int i = 0; i < len; i++) {
            g.add(new Num(ints[i] * 811589153L, i));
        }
        for (int mix = 0; mix < 10; mix++) {
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < len; j++) {
                    Num me = g.get(j);
                    if (me.moveIndex == i) {
                        g.remove(me);
                        g.add((int) ((j + me.n % (len - 1) + (len - 1)) % (len - 1)), me);
                        break;
                    }
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
        long n;
        int moveIndex;

        public Num(long n, int moveIndex) {
            this.n = n;
            this.moveIndex = moveIndex;
        }
    }
}
