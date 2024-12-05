package se.timotej.advent.y2024;

import java.io.IOException;
import java.util.List;

public class Advent4 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent4().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int svar = 0;
        int maxy = strs.size();
        int maxx = strs.get(0).length();
        String xmas = "XMAS";
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dy != 0 || dx != 0) {
                    for (int sy = 0; sy < maxy; sy++) {
                        for (int sx = 0; sx < maxx; sx++) {
                            boolean fel = false;
                            for (int pos = 0; pos < xmas.length(); pos++) {
                                int y = sy + dy*pos;
                                int x = sx + dx*pos;
                                if (x < 0 || x >= maxx || y < 0 || y >= maxy || xmas.charAt(pos) != strs.get(y).charAt(x)) {
                                    fel = true;
                                    break;
                                }
                            }
                            if (!fel) {
                                svar++;
                            }
                        }
                    }
                }
            }
        }
        return svar;
    }
}