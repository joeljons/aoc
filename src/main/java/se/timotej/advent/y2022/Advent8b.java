package se.timotej.advent.y2022;

import java.io.IOException;
import java.util.List;

public class Advent8b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent8b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int maxx = strs.get(0).length();
        int maxy = strs.size();
        int svar = 0;
        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                int len = strs.get(y).charAt(x) - '0';
                int score = 1;

                int subscore = 0;
                for (int xx = x - 1; xx >= 0; xx--) {
                    subscore++;
                    if (strs.get(y).charAt(xx) - '0' >= len) {
                        break;
                    }
                }
                score *= subscore;

                subscore = 0;
                for (int xx = x + 1; xx < maxx; xx++) {
                    subscore++;
                    if (strs.get(y).charAt(xx) - '0' >= len) {
                        break;
                    }
                }
                score *= subscore;

                subscore = 0;
                for (int yy = y - 1; yy >= 0; yy--) {
                    subscore++;
                    if (strs.get(yy).charAt(x) - '0' >= len) {
                        break;
                    }
                }
                score *= subscore;

                subscore = 0;
                for (int yy = y + 1; yy < maxy ; yy++) {
                    subscore++;
                    if (strs.get(yy).charAt(x) - '0' >= len) {
                        break;
                    }
                }
                score *= subscore;

                svar = Math.max(score, svar);
            }
        }
        return svar;
    }
}
