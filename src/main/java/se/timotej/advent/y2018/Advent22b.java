package se.timotej.advent.y2018;

import org.apache.commons.lang3.tuple.Triple;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class Advent22b {

    private static final int EXTRA = 100;

    public static void main(String[] args) throws IOException {
        String svar = new Advent22b().calc(Online.get());
        System.out.println("svar = " + svar);
//        Online.submit(svar);
    }

    int ROCKY = 0;
    int WET = 1;
    int NARROW = 2;

    int NEITHER = 0;
    int TORCH = 1;
    int CLIMB = 2;

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};


    private String calc(List<String> strs) {
        int targetx = 15;
        int targety = 740;
        int depth = 3558;

//        depth = 510;
//        targetx = targety  = 10;

        long[][] g = new long[targety + EXTRA][targetx + EXTRA];
        int[][] G = new int[targety + EXTRA][targetx + EXTRA];
        for (int y = 0; y < targety + EXTRA; y++) {
            for (int x = 0; x < targetx + EXTRA; x++) {
                long gi;
                if (x == 0) {
                    gi = y * 48271;
                } else if (y == 0) {
                    gi = x * 16807;
                } else if (y == targety && x == targetx) {
                    gi = 0;
                } else {
                    gi = g[y][x - 1] * g[y - 1][x];
                }

                gi %= 20183;

                long el = (gi + depth) % 20183;
                g[y][x] = el;
                G[y][x] = (int) (el % 3);
//                System.out.print(el%3 == 0 ? "." : el%3 == 1 ? "=":"|");
            }
//            System.out.println();
        }

        int[][][] state = new int[3][targety + EXTRA][targetx + EXTRA];
        for (int[][] ints : state) {
            for (int[] anInt : ints) {
                Arrays.fill(anInt, Integer.MAX_VALUE);
            }
        }
        Queue<Triple<Integer, Integer, Integer>> q = new ArrayDeque<>();
        q.add(Triple.of(TORCH, 0, 0));
        state[TORCH][0][0] = 0;
        int apa = 0;
        while (!q.isEmpty()) {
            apa++;
            Triple<Integer, Integer, Integer> now = q.remove();
            int tool = now.getLeft();
            int x = now.getMiddle();
            int y = now.getRight();
            for (int newTool = 0; newTool < 3; newTool++) {
                if (tool != newTool && newTool != G[y][x]) {
                    if (state[newTool][y][x] > state[tool][y][x] + 7) {
                        state[newTool][y][x] = state[tool][y][x] + 7;
                        q.add(Triple.of(newTool, x, y));
                    }
                }

            }
            for (int dir = 0; dir < 4; dir++) {
                int nyy = y + dy[dir];
                int nyx = x + dx[dir];
                if (!(nyy < 0 || nyy >= targety + EXTRA || nyx < 0 || nyx >= targetx + EXTRA)) {
                    if (tool != G[nyy][nyx]) {
                        if (state[tool][nyy][nyx] > state[tool][y][x] + 1) {
                            state[tool][nyy][nyx] = state[tool][y][x] + 1;
                            q.add(Triple.of(tool, nyx, nyy));
                        }
                    }
                }
            }

        }
        System.out.println("apa = " + apa);
        int svar = state[TORCH][targety][targetx];
        return String.valueOf(svar);
    }
}

/*
0 rocky  (climb or torch)
1 wet    (climb or neither)
2 narrow (torch neither)


start 0,0 with torch
goal target with torch
 */

//fel 1011