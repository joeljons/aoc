package se.timotej.advent.y2023;

import java.io.IOException;
import java.util.List;

public class Advent13b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent13b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;
        for (List<String> g : Util.splitByDoubleNewline(strs)) {
            svar += getScore(g);
        }
        return svar;
    }

    private long getScore(List<String> g) {
        char[][] G = Util.charMatrix(g);
        int maxx = G[0].length;
        int maxy = G.length;
        long original = getScore(G, -1);

        for (int x = 0; x < maxx; x++) {
            for (int y = 0; y < maxy; y++) {
                char orig = G[y][x];
                char change = orig == '#' ? '.' : '#';
                G[y][x] = change;
                long now = getScore(G, original);
                if (now != 0) {
                    return now;
                }
                G[y][x] = orig;

            }
        }
        throw new RuntimeException();
    }

    private long getScore(char[][] g, long avoid) {
        int maxx = g[0].length;
        int maxy = g.length;
        for (int ref = 0; ref < maxx - 1; ref++) {
            boolean ok = true;
            for (int x0 = ref, x1 = ref + 1; x0 >= 0 && x1 < maxx; x0--, x1++) {
                for (int y = 0; y < maxy; y++) {
                    if (g[y][x0] != g[y][x1]) {
                        ok = false;
                        break;
                    }
                }
                if (!ok) {
                    break;
                }
            }
            if (ok && ref + 1 != avoid) {
                return ref + 1;
            }
        }
        for (int ref = 0; ref < maxy - 1; ref++) {
            boolean ok = true;
            for (int y0 = ref, y1 = ref + 1; y0 >= 0 && y1 < maxy; y0--, y1++) {
                for (int x = 0; x < maxx; x++) {
                    if (g[y0][x] != g[y1][x]) {
                        ok = false;
                        break;
                    }
                }
                if (!ok) {
                    break;
                }
            }
            if (ok && 100 * (ref + 1) != avoid) {
                return 100 * (ref + 1);
            }
        }
        return 0;
    }
}
