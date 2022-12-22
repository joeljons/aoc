package se.timotej.advent.y2022;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.List;

public class Advent22 {
    public static void main(String[] args) throws IOException {
        int svar = new Advent22().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{1, 0, -1, 0};
    int[] dy = new int[]{0, 1, 0, -1};
    int maxx;
    int maxy;

    private int calc(List<String> strs) {
        String inst = strs.get(strs.size() - 1);
        maxy = strs.size() - 2;
        maxx = strs.get(0).length();
        int y = 0;
        int x = 0;
        int dir = 0;
        while (strs.get(0).charAt(x) == ' ') {
            x++;
        }
        int dist = 0;
        for (int i = 0; i < inst.length(); i++) {
            if (Character.isDigit(inst.charAt(i))) {
                dist = 10 * dist + (inst.charAt(i) - '0');
            } else {
                Pair<Integer, Integer> destination = walk(x, y, dir, dist, strs);
                x = destination.getLeft();
                y = destination.getRight();

                dist = 0;
                if (inst.charAt(i) == 'L') {
                    dir = (dir + 3) % 4;
                } else {
                    dir = (dir + 1) % 4;
                }
            }
        }
        Pair<Integer, Integer> destination = walk(x, y, dir, dist, strs);
        x = destination.getLeft();
        y = destination.getRight();
        return 1000 * (y + 1) + 4 * (x + 1) + dir;
    }

    private Pair<Integer, Integer> walk(int x, int y, int dir, int dist, List<String> strs) {
        Pair<Integer, Integer> now = Pair.of(x, y);
        for (int j = 0; j < dist; j++) {
            now = walk(now.getLeft(), now.getRight(), dir, strs);
        }
        return now;
    }

    private Pair<Integer, Integer> walk(int x, int y, int dir, List<String> strs) {
        int xx = (x + dx[dir] + maxx) % maxx;
        int yy = (y + dy[dir] + maxy) % maxy;
        while (strs.get(yy).length() <= xx || strs.get(yy).charAt(xx) == ' ') {
            xx = (xx + dx[dir] + maxx) % maxx;
            yy = (yy + dy[dir] + maxy) % maxy;
        }
        if (strs.get(yy).charAt(xx) == '.') {
            return Pair.of(xx, yy);
        } else {
            return Pair.of(x, y);
        }
    }
}