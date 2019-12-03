package se.timotej.advent.y2017;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Advent14b {
    public static void main(String[] args) throws IOException {
        new Advent14b().calc(Online.get(14));
    }

    int[] dx = {0, 1, 0, -1};
    int[] dy = {1, 0, -1, 0};

    private void calc(List<String> strs) {
        String str = strs.get(0);
        int sum = 0;
        int[][] g = new int[128][];
        for (int i = 0; i < 128; i++) {
            String sToHash = str + "-" + i;
            g[i] = calc2(256, sToHash);
        }
        for (int y = 0; y < 128; y++) {
            for (int x = 0; x < 128; x++) {
                if (g[y][x] == 1) {
                    sum++;
                    Queue<Pair<Integer, Integer>> q = new LinkedList<>();
                    q.add(Pair.of(y, x));
                    g[y][x] = 0;
                    while (!q.isEmpty()) {
                        Pair<Integer, Integer> p = q.poll();
                        for (int i = 0; i < 4; i++) {
                            int xx = p.getValue() + dx[i];
                            int yy = p.getKey() + dy[i];
                            if (xx >= 0 && xx < 128 && yy >= 0 && yy < 128 && g[yy][xx] == 1) {
                                g[yy][xx] = 0;
                                q.add(Pair.of(yy, xx));
                            }
                        }
                    }
                }
            }
        }
        System.out.println("sum = " + sum);
    }


    private int[] calc2(int listLen, String str) {
        String hash = new Advent10b().calc(256, Collections.singletonList(str));
        int k=0;
        int[] ut = new int[256];
        for (int i = 0; i < 32; i++) {
            int sum = Character.digit(hash.charAt(i), 16);
            ut[k++] = (sum & 8) >> 3;
            ut[k++] = (sum & 4) >> 2;
            ut[k++] = (sum & 2) >> 1;
            ut[k++] = sum & 1;
        }
        return ut;
    }
}