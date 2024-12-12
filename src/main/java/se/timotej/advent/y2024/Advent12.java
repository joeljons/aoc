package se.timotej.advent.y2024;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class Advent12 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent12().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};

    private long calc(List<String> strs) {
        long svar = 0;
        char[][] g = Util.charMatrix(strs);
        int maxx = g[0].length;
        int maxy = g.length;
        boolean[][] taken = new boolean[maxy][maxx];
        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                if (!taken[y][x]) {
                    long area = 0;
                    long perimeter = 0;
                    Queue<Pair<Integer, Integer>> q = new ArrayDeque<>();
                    taken[y][x] = true;
                    q.add(Pair.of(x, y));
                    while (!q.isEmpty()) {
                        area++;
                        Pair<Integer, Integer> now = q.remove();
                        int nowx = now.getLeft();
                        int nowy = now.getRight();
                        for (int dir = 0; dir < 4; dir++) {
                            int nx = nowx + dx[dir];
                            int ny = nowy + dy[dir];
                            if (nx >= 0 && ny >= 0 && nx < maxx && ny < maxy && g[ny][nx] == g[y][x]) {
                                if (!taken[ny][nx]) {
                                    taken[ny][nx] = true;
                                    q.add(Pair.of(nx, ny));
                                }
                            } else {
                                perimeter++;
                            }
                        }
                    }
                    svar += area * perimeter;
                }
            }
        }
        return svar;
    }
}
