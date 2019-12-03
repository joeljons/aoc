package se.timotej.advent.y2015;

import java.io.IOException;
import java.util.List;

public class Advent21b {
    public static void main(String[] args) throws IOException {
        int svar = new Advent21b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) { //HP 100, damage 8, armor 2
        int[][] daggers = new int[][]{
                {8, 4, 0},
                {10, 5, 0},
                {25, 6, 0},
                {40, 7, 0},
                {74, 8, 0}};
        int[][] armors = new int[][]{
                {13, 0, 1},
                {31, 0, 2},
                {53, 0, 3},
                {75, 0, 4},
                {102, 0, 5},
        };
        int[][] rings = new int[][]{
                {25, 1, 0},
                {50, 2, 0},
                {100, 3, 0},
                {20, 0, 1},
                {40, 0, 2},
                {80, 0, 3},
        };
        int best = Integer.MIN_VALUE;
        for (int di = 0; di < daggers.length; di++) {
            for (int ai = -1; ai < armors.length; ai++) {
                for (int ri1 = -1; ri1 < rings.length; ri1++) {
                    for (int ri2 = -1; ri2 < rings.length; ri2++) {
                        if (ri1 >= 0 && ri1 == ri2) {
                            continue;
                        }
                        int[] sum = new int[3];
                        for (int i = 0; i < 3; i++) {
                            sum[i] = choose(daggers, di, i)
                                    + choose(armors, ai, i)
                                    + choose(rings, ri1, i)
                                    + choose(rings, ri2, i);
                        }
                        int cost = sum[0];
                        sum[0] = 100;
                        if (cost > best && !winsFight(sum, new int[]{100, 8, 2})) {
                            best = cost;
                        }
                    }
                }
            }
        }
        return best;
    }

    private boolean winsFight(int[] me, int[] opponent) {
        while (true) {
            opponent[0] -= Math.max(1, me[1] - opponent[2]);
            if (opponent[0] <= 0) {
                return true;
            }
            me[0] -= Math.max(1, opponent[1] - me[2]);
            if (me[0] <= 0) {
                return false;
            }
        }
    }

    private int choose(int[][] items, int index, int pickIndex) {
        if (index < 0) {
            return 0;
        }
        return items[index][pickIndex];
    }
}
