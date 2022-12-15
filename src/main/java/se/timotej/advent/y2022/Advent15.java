package se.timotej.advent.y2022;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Advent15 {
    public static void main(String[] args) throws IOException {
        int svar = new Advent15().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int svar = 0;
        Map<Integer, Character> g = new TreeMap<>();
        for (String str : strs) {
            List<Integer> allInts = Util.findAllInts(str);
            int sx = allInts.get(0);
            int sy = allInts.get(1);
            int bx = allInts.get(2);
            int by = allInts.get(3);
            int dist = Math.abs(bx - sx) + Math.abs(by - sy);
            int xdist = dist - Math.abs(2000000 - sy);
            for (int x = sx - xdist; x <= sx + xdist; x++) {
                if (by == 2000000 && bx == x) {
                    g.put(x, 'B');
                }
                g.putIfAbsent(x, '#');
            }
        }
        for (Character value : g.values()) {
            char c = value;
            if (c == '#') {
                svar++;
            }
        }
        return svar;
    }
}
