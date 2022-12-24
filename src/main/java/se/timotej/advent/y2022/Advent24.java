package se.timotej.advent.y2022;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Advent24 {
    public static void main(String[] args) throws IOException {
        int svar = new Advent24().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = {0, 0, -1, 1, 0};
    int[] dy = {-1, 1, 0, 0, 0};
    int maxy;
    int maxx;

    private int calc(List<String> strs) {
        int svar = 0;
        maxy = strs.size();
        maxx = strs.get(0).length();
        List<Blizzard> blizzards = new ArrayList<>();
        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                char c = strs.get(y).charAt(x);
                if (c != '#' && c != '.') {
                    blizzards.add(new Blizzard(x, y, c));
                }
            }
        }
        Set<Pair<Integer, Integer>> now = new HashSet<>();
        now.add(Pair.of(1, 0));
        while (!now.contains(Pair.of(maxx - 2, maxy - 1))) {
            svar++;
            Set<Pair<Integer, Integer>> blizPos = new TreeSet<>();
            for (Blizzard blizzard : blizzards) {
                blizzard.move();
                blizPos.add(Pair.of(blizzard.x, blizzard.y));
            }
            Set<Pair<Integer, Integer>> next = new HashSet<>();
            for (Pair<Integer, Integer> nowPos : now) {
                int x = nowPos.getLeft();
                int y = nowPos.getRight();
                for (int dir = 0; dir < 5; dir++) {
                    int xx = x + dx[dir];
                    int yy = y + dy[dir];
                    if (xx < 0 || xx >= maxx || yy < 0 || yy >= maxy || strs.get(yy).charAt(xx) == '#' || blizPos.contains(Pair.of(xx, yy))) {
                        continue;
                    }
                    next.add(Pair.of(xx, yy));
                }
            }
            now = next;
        }
        return svar;
    }

    private class Blizzard {
        int x;
        int y;
        int dx;
        int dy;

        public Blizzard(int x, int y, char dirChar) {
            this.x = x;
            this.y = y;
            if (dirChar == '^') {
                dy = -1;
            }
            if (dirChar == 'v') {
                dy = 1;
            }
            if (dirChar == '<') {
                dx = -1;
            }
            if (dirChar == '>') {
                dx = 1;
            }
        }

        public void move() {
            x = x + dx;
            y = y + dy;
            if (x == maxx - 1) {
                x = 1;
            }
            if (x == 0) {
                x = maxx - 2;
            }
            if (y == maxy - 1) {
                y = 1;
            }
            if (y == 0) {
                y = maxy - 2;
            }
        }
    }
}
//135