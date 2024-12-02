package se.timotej.advent.y2024;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Advent2b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent2b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int svar = 0;
        int[][] g = Util.intMatrix(strs);
        for (int i = 0; i < g.length; i++) {
            boolean foundOk = false;
            for (int skip = -1; skip < g[i].length; skip++) {
                List<Integer> list = new ArrayList<>(Arrays.stream(g[i]).boxed().toList());
                if (skip != -1) {
                    list.remove(skip);
                }
                boolean fel = false;
                boolean inc = list.get(1) > list.get(0);
                for (int j = 1; j < list.size(); j++) {
                    if (list.get(j) > list.get(j - 1) != inc || Math.abs(list.get(j) - list.get(j - 1)) < 1 || Math.abs(list.get(j) - list.get(j - 1)) > 3) {
                        fel = true;
                        break;
                    }
                }
                if (!fel) {
                    foundOk = true;
                    break;
                }
            }
            if (foundOk) {
                svar++;
            }
        }
        return svar;
    }
}
