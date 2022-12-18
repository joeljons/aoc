package se.timotej.advent.y2022;

import java.io.IOException;
import java.util.List;

public class Advent18 {
    public static void main(String[] args) throws IOException {
        int svar = new Advent18().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{-1, 1, 0, 0, 0, 0};
    int[] dy = new int[]{0, 0, -1, 1, 0, 0};
    int[] dz = new int[]{0, 0, 0, 0, -1, 1};


    private int calc(List<String> strs) {
        int svar = 0;
        boolean[][][] g = new boolean[30][30][30];
        for (String str : strs) {
            List<Integer> allInts = Util.findAllInts(str);
            int z = allInts.get(2) + 1;
            int y = allInts.get(1) + 1;
            int x = allInts.get(0) + 1;
            g[z][y][x] = true;
        }
        for (String str : strs) {
            List<Integer> allInts = Util.findAllInts(str);
            int z = allInts.get(2) + 1;
            int y = allInts.get(1) + 1;
            int x = allInts.get(0) + 1;
            svar += 6;
            for (int dir = 0; dir < 6; dir++) {
                int nx = x + dx[dir];
                int ny = y + dy[dir];
                int nz = z + dz[dir];
                if (g[nz][ny][nx]) {
                    svar--;
                }
            }
        }
        return svar;
    }
}
