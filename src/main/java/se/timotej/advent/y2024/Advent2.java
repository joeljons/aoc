package se.timotej.advent.y2024;

import java.io.IOException;
import java.util.List;

public class Advent2 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent2().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int svar = 0;
        int[][] g = Util.intMatrix(strs);
        for (int i = 0; i < g.length; i++) {
            boolean inc = g[i][1] > g[i][0];
            boolean fel = false;
            for (int j = 1; j < g[i].length; j++) {
                if (g[i][j] > g[i][j - 1] != inc || Math.abs(g[i][j] - g[i][j - 1]) < 1 || Math.abs(g[i][j] - g[i][j - 1]) > 3) {
                    fel = true;
                    break;
                }
            }
            if (!fel) {
                svar++;
            }
        }
        return svar;
    }
}
