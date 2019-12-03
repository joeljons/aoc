package se.timotej.advent.y2017;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent13b {
    public static void main(String[] args) throws IOException {
        new Advent13b().calc(Online.get(13));
    }

    private void calc(List<String> strs) {
        Map<Integer, Apa> g = new HashMap<>();
        int max = 0;
        for (String str : strs) {
            String[] split = str.split(": ");
            int pos = Integer.parseInt(split[0]);
            int depth = Integer.parseInt(split[1]);
            g.put(pos, new Apa(depth));
            max = Math.max(max, pos);
        }
        for (int delay = 0; true; delay++) {
            if (delay % 1000000 == 0) {
                System.out.println("delay = " + delay);
            }
            for (Map.Entry<Integer, Apa> integerApaEntry : g.entrySet()) {
                int i = integerApaEntry.getKey();

                Apa gapa = integerApaEntry.getValue();
                gapa.curINIT += gapa.dirINIT;
                if (gapa.curINIT == 0) {
                    gapa.dirINIT = 1;
                }
                if (gapa.curINIT == gapa.depth - 1) {
                    gapa.dirINIT = -1;
                }
                gapa.cur = gapa.curINIT;
                gapa.dir = gapa.dirINIT;
            }

            if (isSafe(g, max)) {
                System.out.println("funkar");
                System.out.println("delay+1 = " + (delay + 1));
                break;
            }
        }
    }

    private boolean isSafe(Map<Integer, Apa> g, int max) {
        int sev = 0;
        for (int pos = 0; pos <= max; pos++) {
            Apa apa = g.get(pos);
            if (apa != null) {
                if (apa.cur == 0) {
                    return false;
                }
            }
            for (int i = 0; i <= max; i++) {
                Apa gapa = g.get(i);
                if (gapa == null) {
                    continue;
                }
                gapa.cur += gapa.dir;
                if (gapa.cur == 0) {
                    gapa.dir = 1;
                }
                if (gapa.cur == gapa.depth - 1) {
                    gapa.dir = -1;
                }
            }

        }
        return true;
    }

    private class Apa {
        private int depth;
        private int cur = 0;
        private int dir = 1;
        private int curINIT = 0;
        private int dirINIT = 1;

        public Apa(int depth) {
            this.depth = depth;
        }
    }
}