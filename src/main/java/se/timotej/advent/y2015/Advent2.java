package se.timotej.advent.y2015;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Advent2 {
    public static void main(String[] args) throws IOException {
        new Advent2().calc(Online.get(2));
    }

    private void calc(List<String> strs) {
        int paper = 0;
        int ribbon = 0;
        for (String str : strs) {
            String[] split = str.split("x");
            int g[] = new int[]{Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])};
            Arrays.sort(g);
            paper += 3 * g[0]*g[1] + 2*g[1]*g[2] + 2*g[0]*g[2];
            ribbon += 2*g[0] + 2*g[1] + g[0]*g[1]*g[2];
        }
        System.out.println("paper = " + paper);
        System.out.println("ribbon = " + ribbon);
    }

}
