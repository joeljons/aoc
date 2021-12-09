package se.timotej.advent.y2021;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class Advent9b {

    public static void main(String[] args) throws IOException {
        var svar = new Advent9b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};

    private int calc(List<String> strs) {
        int sum = 1;
        int maxx = strs.get(0).length();
        int maxy = strs.size();
        boolean[][] taken = new boolean[maxy][maxx];
        List<Integer> sizes = new ArrayList<>();
        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                if (taken[y][x]) {
                    continue;
                }
                if (strs.get(y).charAt(x) == '9') {
                    continue;
                }
                int size = 0;
                Queue<Pair<Integer, Integer>> q = new ArrayDeque<>();
                q.add(Pair.of(x, y));
                taken[y][x] = true;
                while (!q.isEmpty()) {
                    size++;
                    Pair<Integer, Integer> nu = q.poll();
                    int xx = nu.getLeft();
                    int yy = nu.getRight();
                    for (int dir = 0; dir < 4; dir++) {
                        int x2 = xx + dx[dir];
                        int y2 = yy + dy[dir];
                        if (x2 >= 0 && x2 < maxx && y2 >= 0 && y2 < maxy && !taken[y2][x2] && strs.get(y2).charAt(x2) != '9') {
                            taken[y2][x2] = true;
                            q.add(Pair.of(x2, y2));
                        }
                    }
                }
                sizes.add(size);
            }
        }
        Collections.sort(sizes);
        for (int i = sizes.size() - 3; i < sizes.size(); i++) {
            sum *= sizes.get(i);
        }
        return sum;
    }

}
