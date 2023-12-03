package se.timotej.advent.y2023;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.*;

public class Advent3b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent3b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{0, 1, 1, 1, 0, -1, -1, -1};
    int[] dy = new int[]{-1, -1, 0, 1, 1, 1, 0, -1};

    private int calc(List<String> strs) {
        int svar = 0;
        int maxy = strs.size();
        int maxx = strs.get(0).length();
        Map<Pair<Integer, Integer>, List<Integer>> gears = new HashMap<>();
        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                int startx = x;
                char c = strs.get(y).charAt(x);
                if (Character.isDigit(c)) {
                    int tal = 0;
                    boolean connected = false;
                    while (Character.isDigit(c) && x < maxx) {
                        tal = 10 * tal + c - '0';
                        for (int dir = 0; dir < 8; dir++) {
                            int nyx = x + dx[dir];
                            int nyy = y + dy[dir];
                            if (nyx >= 0 && nyx < maxx && nyy >= 0 && nyy < maxy && !Character.isDigit(strs.get(nyy).charAt(nyx)) && strs.get(nyy).charAt(nyx) != '.') {
                                connected = true;
                            }
                        }
                        x++;
                        if (x < maxx) {
                            c = strs.get(y).charAt(x);
                        }
                    }
                    if (connected) {
                        Set<Pair<Integer, Integer>> nowTaken = new HashSet<>();
                        for (int xx = startx; xx <= x && xx < maxx && Character.isDigit(strs.get(y).charAt(xx)); xx++) {
                            for (int dir = 0; dir < 8; dir++) {
                                int nyx = xx + dx[dir];
                                int nyy = y + dy[dir];
                                if (nyx >= 0 && nyx < maxx && nyy >= 0 && nyy < maxy && strs.get(nyy).charAt(nyx) == '*') {
                                    Pair<Integer, Integer> key = Pair.of(nyx, nyy);
                                    if (nowTaken.add(key)) {
                                        gears.computeIfAbsent(key, k -> new ArrayList<>()).add(tal);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        for (List<Integer> value : gears.values()) {
            if (value.size() == 2) {
                svar += value.get(0) * value.get(1);
            }
        }
        return svar;
    }
}
