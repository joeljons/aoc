package se.timotej.advent.y2022;

import java.io.IOException;
import java.util.List;

public class Advent17 {
    public static void main(String[] args) throws IOException {
        int svar = new Advent17().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    char[][][] rocks = new char[][][]{
            {{'#', '#', '#', '#'}},
            {{'.', '#', '.'},
                    {'#', '#', '#'},
                    {'.', '#', '.'}},
            {{'#', '#', '#'},
                    {'.', '.', '#'},
                    {'.', '.', '#'}},
            {{'#'}, {'#'}, {'#'}, {'#'}},
            {{'#', '#'},
                    {'#', '#'}},
    };

    private int calc(List<String> strs) {
        int height = 0;
        char[][] g = new char[10000][7];
        String jets = strs.get(0);
        int blowi = 0;
        for (int rocki = 0; rocki < 2022; rocki++) {
            char[][] rock = rocks[rocki % rocks.length];
            int x = 2;
            int y = height + 3;

            x += blow(x, y, rock, g, jets.charAt(blowi) == '<' ? -1 : 1);
            blowi = (blowi + 1) % jets.length();
            while (!atRest(x, y, rock, g)) {
                y--;
                x += blow(x, y, rock, g, jets.charAt(blowi) == '<' ? -1 : 1);
                blowi = (blowi + 1) % jets.length();
            }
            for (int yy = 0; yy < rock.length; yy++) {
                for (int xx = 0; xx < rock[yy].length; xx++) {
                    if (rock[yy][xx] == '#') {
                        g[y + yy][x + xx] = '#';
                        height = Math.max(height, y + yy + 1);
                    }
                }
            }
        }
        return height;
    }

    private int blow(int x, int y, char[][] rock, char[][] g, int dx) {
        for (int yy = 0; yy < rock.length; yy++) {
            for (int xx = 0; xx < rock[yy].length; xx++) {
                if (rock[yy][xx] != '#') {
                    continue;
                }
                int nyx = xx + x + dx;
                int nyy = yy + y;
                if (nyx < 0 || nyx >= 7) {
                    return 0;
                }
                if (g[nyy][nyx] == '#') {
                    return 0;
                }
            }
        }
        return dx;
    }

    private boolean atRest(int x, int y, char[][] rock, char[][] g) {
        for (int yy = 0; yy < rock.length; yy++) {
            for (int xx = 0; xx < rock[yy].length; xx++) {
                if (rock[yy][xx] != '#') {
                    continue;
                }
                int nyx = xx + x;
                int nyy = yy + y - 1;
                if (nyy < 0) {
                    return true;
                }
                if (g[nyy][nyx] == '#') {
                    return true;
                }
            }
        }
        return false;
    }
}
