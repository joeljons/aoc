package se.timotej.advent.y2022;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent8 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent8().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int maxx = strs.get(0).length();
        int maxy = strs.size();
        Set<String> taken = new HashSet<>();
        for (int y = 0; y < maxy; y++) {
            int len = -1;
            for (int x = 0; x < maxx; x++) {
                if (strs.get(y).charAt(x) - '0' > len) {
                    taken.add(x + "_" + y);
                }
                len = Math.max(strs.get(y).charAt(x) - '0', len);
            }
            len = -1;
            for (int x = maxx - 1; x >= 0; x--) {
                if (strs.get(y).charAt(x) - '0' > len) {
                    taken.add(x + "_" + y);
                }
                len = Math.max(strs.get(y).charAt(x) - '0', len);
            }
        }

        for (int x = 0; x < maxx; x++) {
            int len = -1;
            for (int y = 0; y < maxx; y++) {
                if (strs.get(y).charAt(x) - '0' > len) {
                    taken.add(x + "_" + y);
                }
                len = Math.max(strs.get(y).charAt(x) - '0', len);
            }
            len = -1;
            for (int y = maxx - 1; y >= 0; y--) {
                if (strs.get(y).charAt(x) - '0' > len) {
                    taken.add(x + "_" + y);
                }
                len = Math.max(strs.get(y).charAt(x) - '0', len);
            }
        }
        return taken.size();
    }
}
