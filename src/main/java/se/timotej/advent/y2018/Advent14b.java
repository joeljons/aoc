package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Advent14b {

    public static void main(String[] args) throws IOException {
        String svar = new Advent14b().calc(Online.get());
        System.out.println("svar = " + svar);
//        Online.submit(svar);
    }

    private String calc(List<String> strs) {
        List<Integer> g = new ArrayList<>();
        int p1 = 0;
        int p2 = 1;
        g.add(3);
        g.add(7);
        while (true) {
            int next = g.get(p1) + g.get(p2);
            if (next >= 10) {
                g.add(1);
                if (g.size() > 5 &&
                        g.get(g.size() - 6) == 5
                        && g.get(g.size() - 5) == 5
                        && g.get(g.size() - 4) == 6
                        && g.get(g.size() - 3) == 0
                        && g.get(g.size() - 2) == 6
                        && g.get(g.size() - 1) == 1) {
                    return String.valueOf(g.size() - 6);
                }
            }
            g.add(next % 10);
            if (g.size() > 5 &&
                    g.get(g.size() - 6) == 5
                    && g.get(g.size() - 5) == 5
                    && g.get(g.size() - 4) == 6
                    && g.get(g.size() - 3) == 0
                    && g.get(g.size() - 2) == 6
                    && g.get(g.size() - 1) == 1) {
                return String.valueOf(g.size() - 6);
            }
//            System.out.println("g = " + g);
            p1 = (p1 + g.get(p1) + 1) % g.size();
            p2 = (p2 + g.get(p2) + 1) % g.size();
        }
    }
}