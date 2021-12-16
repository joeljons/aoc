package se.timotej.advent.y2021;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Advent15b {

    public static void main(String[] args) throws IOException {
        var svar = new Advent15b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};


    private int calc(List<String> strs) {
        int origMaxy = strs.size();
        int maxy = origMaxy * 5;
        int origMaxx = strs.get(0).length();
        int maxx = origMaxx * 5;
        int[][] risk = new int[maxy][maxx];
        Queue<Pair<Integer, Pair<Integer, Integer>>> q = new PriorityQueue<>();
        q.add(Pair.of(0, Pair.of(0, 0)));
        while (!q.isEmpty()) {
            Pair<Integer, Pair<Integer, Integer>> entry = q.poll();
            Pair<Integer, Integer> nu = entry.getRight();
            int xx = nu.getLeft();
            int yy = nu.getRight();
            if (risk[yy][xx] != 0) {
                continue;
            }
            risk[yy][xx] = entry.getLeft();
            for (int dir = 0; dir < 4; dir++) {
                int x2 = xx + dx[dir];
                int y2 = yy + dy[dir];
                if (x2 >= 0 && x2 < maxx && y2 >= 0 && y2 < maxy) {
                    char c = strs.get(y2 % origMaxy).charAt(x2 % origMaxx);
                    c += y2 / origMaxy;
                    c += x2 / origMaxx;
                    while (c > '9') {
                        c -= 9;
                    }
                    q.add(Pair.of(risk[yy][xx] + (c - '0'), Pair.of(x2, y2)));
                }
            }
        }

        return risk[maxy - 1][maxx - 1];
    }

}
