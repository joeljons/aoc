package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.List;

public class Advent18b {

    public static void main(String[] args) throws IOException {
        String svar = new Advent18b().calc(Online.get());
        System.out.println("svar = " + svar);
        //Online.submit(svar);
    }

    char[][] g;

    private String calc(List<String> strs) {
        int svar = 0;
        int maxy = strs.size();
        int maxx = strs.get(0).length();
        g = new char[maxy][];
        {
            int y = 0;
            for (String str : strs) {
                g[y++] = str.toCharArray();
            }
        }
        int target = (1_000_000_000%28)+28*100;
        System.out.println("target = " + target);
        for (int i = 0; i < target; i++) {
            //svar = calcSvar(maxy, maxx);
            //System.out.println("svar = " + svar);
            char[][] next = new char[maxy][maxx];
            for (int y = 0; y < maxy; y++) {
                for (int x = 0; x < maxx; x++) {
                    int tree = 0;
                    int lumbs = 0;
                    for (int dy = -1; dy <= 1; dy++) {
                        for (int dx = -1; dx <= 1; dx++) {
                            if (dy == 0 && dx == 0) {
                                continue;
                            }
                            if (y + dy < 0) {
                                continue;
                            }
                            if (x + dx < 0) {
                                continue;
                            }
                            if (y + dy >= maxy) {
                                continue;
                            }
                            if (x + dx >= maxx) {
                                continue;
                            }

                            if (g[y + dy][x + dx] == '|') {
                                tree++;
                            }
                            if (g[y + dy][x + dx] == '#') {
                                lumbs++;
                            }
                        }
                        next[y][x] = g[y][x];
                        if (g[y][x] == '.' && tree >= 3) {
                            next[y][x] = '|';
                        } else if (g[y][x] == '|' && lumbs >= 3) {
                            next[y][x] = '#';
                        } else if (g[y][x] == '#') {
                            if (lumbs >= 1 && tree >= 1) {
                                next[y][x] = '#';
                            } else {
                                next[y][x] = '.';
                            }
                        }
                    }
                }
            }
            g = next;
        }
        svar = calcSvar(maxy, maxx);
        return String.valueOf(svar);
    }

    private int calcSvar(int maxy, int maxx) {
        int svar;
        int tree = 0;
        int lumbs = 0;
        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                if (g[y][x] == '|') {
                    tree++;
                }
                if (g[y][x] == '#') {
                    lumbs++;
                }
            }
        }
        svar = tree * lumbs;
        return svar;
    }

}