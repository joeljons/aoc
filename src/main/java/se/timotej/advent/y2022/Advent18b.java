package se.timotej.advent.y2022;

import org.apache.commons.lang3.tuple.Triple;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class Advent18b {
    public static void main(String[] args) throws IOException {
        int svar = new Advent18b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{-1, 1, 0, 0, 0, 0};
    int[] dy = new int[]{0, 0, -1, 1, 0, 0};
    int[] dz = new int[]{0, 0, 0, 0, -1, 1};


    private int calc(List<String> strs) {
        int svar = 0;
        int[][][] g = new int[30][30][30];
        for (String str : strs) {
            List<Integer> allInts = Util.findAllInts(str);
            int x = allInts.get(0) + 1;
            int y = allInts.get(1) + 1;
            int z = allInts.get(2) + 1;
            g[z][y][x] = 1;
        }
        Queue<Triple<Integer, Integer, Integer>> q = new ArrayDeque<>();
        q.add(Triple.of(0, 0, 0));
        g[0][0][0] = 2;
        while (!q.isEmpty()) {
            Triple<Integer, Integer, Integer> now = q.remove();
            Integer x = now.getLeft();
            Integer y = now.getMiddle();
            Integer z = now.getRight();
            for (int dir = 0; dir < 6; dir++) {
                int nx = x + dx[dir];
                int ny = y + dy[dir];
                int nz = z + dz[dir];
                if (nx >= 0 && ny >= 0 && nz >= 0 && nx < 30 && ny < 30 && nz < 30 && g[nz][ny][nx] == 0) {
                    g[nz][ny][nx] = 2;
                    q.add(Triple.of(nx, ny, nz));
                }
            }
        }
        for (String str : strs) {
            List<Integer> allInts = Util.findAllInts(str);
            int x = allInts.get(0) + 1;
            int y = allInts.get(1) + 1;
            int z = allInts.get(2) + 1;
            svar += 6;
            for (int dir = 0; dir < 6; dir++) {
                int nx = x + dx[dir];
                int ny = y + dy[dir];
                int nz = z + dz[dir];
                if (g[nz][ny][nx] != 2) {
                    svar--;
                }
            }
        }
        return svar;
    }
}
