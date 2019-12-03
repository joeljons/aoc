package se.timotej.advent.y2017;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Advent25 {
    public static void main(String[] args) throws IOException {
        new Advent25().calc();
    }

    private void calc() {
        Map<Integer, Boolean> g = new HashMap<>();
        char state = 'A';
        int p = 0;
        for (int it = 0; it < 12523873; it++) {
            if (state == 'A') {
                if (!g.getOrDefault(p, false)) {
                    g.put(p, true);
                    p++;
                    state = 'B';
                } else {
                    g.put(p, true);
                    p--;
                    state = 'E';
                }
            } else if (state == 'B') {
                if (!g.getOrDefault(p, false)) {
                    g.put(p, true);
                    p++;
                    state = 'C';
                } else {
                    g.put(p, true);
                    p++;
                    state = 'F';
                }
            } else if (state == 'C') {
                if (!g.getOrDefault(p, false)) {
                    g.put(p, true);
                    p--;
                    state = 'D';
                } else {
                    g.put(p, false);
                    p++;
                    state = 'B';
                }
            } else if (state == 'D') {
                if (!g.getOrDefault(p, false)) {
                    g.put(p, true);
                    p++;
                    state = 'E';
                } else {
                    g.put(p, false);
                    p--;
                    state = 'C';
                }
            } else if (state == 'E') {
                if (!g.getOrDefault(p, false)) {
                    g.put(p, true);
                    p--;
                    state = 'A';
                } else {
                    g.put(p, false);
                    p++;
                    state = 'D';
                }
            } else if (state == 'F') {
                if (!g.getOrDefault(p, false)) {
                    g.put(p, true);
                    p++;
                    state = 'A';
                } else {
                    g.put(p, true);
                    p++;
                    state = 'C';
                }
            }
        }
        int svar = 0;
        for (Boolean aBoolean : g.values()) {
            if (aBoolean) {
                svar++;
            }
        }
        Online.submit(25, 1, svar);
    }


}