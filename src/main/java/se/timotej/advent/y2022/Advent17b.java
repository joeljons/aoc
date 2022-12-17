package se.timotej.advent.y2022;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent17b {
    public static void main(String[] args) throws IOException {
        long svar = new Advent17b().calc(Online.get());
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

    private long calc(List<String> strs) {
        int height = 0;
        char[][] g = new char[10000][7];
        for (char[] chars : g) {
            Arrays.fill(chars, '.');
        }
        String jets = strs.get(0);
        int blowi = 0;
        Map<Triple<Integer, Integer, String>, Pair<Long, Long>> lastSeen = new HashMap<>();
        long extraHeight = 0;
        boolean hyperjumped = false;
        for (long rocki = 0; rocki < 1000000000000L; rocki++) {
            char[][] rock = rocks[(int) (rocki % rocks.length)];
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
            if (!hyperjumped) {
                boolean filled = true;
                for (int i = 0; i < 7; i++) {
                    if (height > 2 && g[height - 2][i] != '#') {
                        filled = false;
                        break;
                    }
                }
                if (filled) {
                    Triple<Integer, Integer, String> key = Triple.of((int) (rocki % 5), blowi, new String(g[height - 1]));
                    System.out.println("key = " + key);
                    System.out.println("rocki = " + rocki);
                    if (lastSeen.containsKey(key)) {
                        Pair<Long, Long> longIntegerPair = lastSeen.get(key);
                        Long lastRocki = longIntegerPair.getLeft();
                        Long lastTotHeight = longIntegerPair.getRight();
                        long loopLength = rocki - lastRocki;
                        long loopHeight = height + extraHeight - lastTotHeight;
                        long jumps = (1000000000000L - rocki) / loopLength;
                        rocki += jumps * loopLength;
                        extraHeight += jumps * loopHeight;
                        hyperjumped = true;
                    } else {
                        lastSeen.put(key, Pair.of(rocki, height + extraHeight));
                    }
                }
            }
        }
        return height + extraHeight;
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
