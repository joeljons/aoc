package se.timotej.advent.y2024;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class Advent12b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent12b().calc(Online.get());
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
        int[][] taken = new int[maxy][maxx];
        int areaIndex = 0;
        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                if (taken[y][x] == 0) {
                    areaIndex++;
                    long area = 0;
                    long perimeter = 0;
                    Queue<Pair<Integer, Integer>> q = new ArrayDeque<>();
                    taken[y][x] = areaIndex;
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
                                if (taken[ny][nx] == 0) {
                                    taken[ny][nx] = areaIndex;
                                    q.add(Pair.of(nx, ny));
                                }
                            }
                        }
                    }
                    for (int ny = 0; ny < maxy; ny++) {
                        boolean over = false;
                        boolean under = false;
                        for (int nx = 0; nx < maxx; nx++) {
                            boolean nextOver = taken[ny][nx] == areaIndex && !(ny > 0 && taken[ny - 1][nx] == areaIndex);
                            boolean nextUnder = taken[ny][nx] == areaIndex && !(ny < maxy - 1 && taken[ny + 1][nx] == areaIndex);
                            if (over && !nextOver) {
                                perimeter++;
                            }
                            if (under && !nextUnder) {
                                perimeter++;
                            }
                            over = nextOver;
                            under = nextUnder;
                        }
                        if (over) {
                            perimeter++;
                        }
                        if (under) {
                            perimeter++;
                        }
                    }
                    for (int nx = 0; nx < maxx; nx++) {
                        boolean left = false;
                        boolean right = false;
                        for (int ny = 0; ny < maxy; ny++) {
                            boolean nextLeft = taken[ny][nx] == areaIndex && !(nx > 0 && taken[ny][nx - 1] == areaIndex);
                            boolean nextRight = taken[ny][nx] == areaIndex && !(nx < maxx - 1 && taken[ny][nx + 1] == areaIndex);
                            if (right && !nextLeft) {
                                perimeter++;
                            }
                            if (left && !nextRight) {
                                perimeter++;
                            }
                            right = nextLeft;
                            left = nextRight;
                        }
                        if (right) {
                            perimeter++;
                        }
                        if (left) {
                            perimeter++;
                        }
                    }
                    svar += area * perimeter;
                }
            }
        }
        return svar;
    }
}
