package se.timotej.advent.y2021;

import java.io.IOException;
import java.util.List;

public class Advent9 {

    public static void main(String[] args) throws IOException {
        var svar = new Advent9().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int sum = 0;
        int maxx = strs.get(0).length();
        int maxy = strs.size();
        boolean[][] taken = new boolean[maxy][maxx];
        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                if (taken[y][x]) {
                    continue;
                }
                boolean storre = false;
                if (x > 0 && strs.get(y).charAt(x) >= strs.get(y).charAt(x - 1)) {
                    storre = true;
                }
                if (x < maxx - 1 && strs.get(y).charAt(x) >= strs.get(y).charAt(x + 1)) {
                    storre = true;
                }
                if (y > 0 && strs.get(y).charAt(x) >= strs.get(y - 1).charAt(x)) {
                    storre = true;
                }
                if (y < maxy - 1 && strs.get(y).charAt(x) >= strs.get(y + 1).charAt(x)) {
                    storre = true;
                }
                if (!storre) {
                    sum += strs.get(y).charAt(x) - '0' + 1;
                    taken[y][x] = true;
                }
            }
        }
        return sum;
    }

}
