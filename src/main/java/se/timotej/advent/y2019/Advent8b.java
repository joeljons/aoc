package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.List;

public class Advent8b {

    public static final int MAXX = 25;
    public static final int MAXY = 6;

    public static void main(String[] args) throws IOException {
        int svar = new Advent8b().calc(Online.get(8));
        System.out.println("svar = " + svar);
        Online.submit(8,2,"YGRYZ");
    }

    private int calc(List<String> strs) {
        int svar = 0;

        String str = strs.get(0);

        char[] g = new char[MAXX * MAXY];
        for (int i = str.length() / (MAXX * MAXY) - 1; i >= 0; i--) {
            for (int j = 0; j < (MAXX * MAXY); j++) {
                char c = str.charAt((MAXX * MAXY) * i + j);
                if (c != '2') {
                    g[j] = c;
                }
            }
        }

        for (int y = 0; y < MAXY; y++) {
            for (int x = 0; x < MAXX; x++) {
                System.out.print(g[y * MAXX + x] == '0' ? ' ' : 'X');
            }
            System.out.println();
        }

        return svar;
    }
}
