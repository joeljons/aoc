package se.timotej.advent.y2023;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.*;

public class Advent23 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent23().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};

    char[] singleDir = "^>v<".toCharArray();


    private long calc(List<String> strs) {
        long svar = 0;
        char[][] g = Util.charMatrix(strs);
        int maxx = g[0].length;
        int maxy = g.length;
        Queue<Pair<Integer, Integer>> q = new ArrayDeque<>();
        Pair<Integer, Integer> start = Pair.of(1, 0);
        Map<Pair<Integer, Integer>, Integer> len = new HashMap<>();
        q.add(start);
        len.put(start, 0);
        while (!q.isEmpty()) {
            Pair<Integer, Integer> now = q.remove();
            int x = now.getLeft();
            int y = now.getRight();
            for (int dir = 0; dir < 4; dir++) {
                int xx = x + dx[dir];
                int yy = y + dy[dir];
                if (xx > 0 && xx < maxx && yy > 0 && yy < maxy && g[yy][xx] != '#') {
                    if (g[y][x] != '.' && g[y][x] != singleDir[dir]) {
                        continue;
                    }
                    int newLen = len.get(now) + 1;
                    Pair<Integer, Integer> next = Pair.of(xx, yy);
                    if (len.containsKey(next) && newLen <= len.get(next) + 2) {
                        continue;
                    }
                    q.add(next);
                    len.put(next, newLen);
                    if (yy == maxy - 1) {
                        svar = newLen;
                    }
                }
            }
        }
        return svar;
    }
}
