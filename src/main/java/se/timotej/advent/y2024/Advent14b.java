package se.timotej.advent.y2024;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class Advent14b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent14b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        int maxx = 101;
        int maxy = 103;
        int biggestBlob = 0;
        int biggestSecond = 0;
        for (int seconds = 0; seconds <= maxx * maxy; seconds++) {
            int[][] g = new int[maxy][maxx];
            int xmin = maxx;
            int xmax = 0;
            int ymin = maxy;
            int ymax = 0;
            for (String str : strs) {
                List<Long> line = Util.findAllLongs(str);
                long x = line.get(0);
                long y = line.get(1);
                long dx = (line.get(2) + maxx) % maxx;
                long dy = (line.get(3) + maxy) % maxy;
                x = (x + seconds * dx) % maxx;
                y = (y + seconds * dy) % maxy;
                xmin = Math.min(xmin, (int) x);
                xmax = Math.max(xmax, (int) x);
                ymin = Math.min(ymin, (int) y);
                ymax = Math.max(ymax, (int) y);
                g[(int) y][(int) x]++;
            }

            boolean[][] taken = new boolean[maxy][maxx];
            for (int y = 0; y < maxy; y++) {
                for (int x = 0; x < maxx; x++) {
                    if (!taken[y][x] && g[y][x] > 0) {
                        Queue<Pair<Integer, Integer>> q = new ArrayDeque<>();
                        q.add(Pair.of(x, y));
                        taken[y][x] = true;
                        int size = 0;
                        while (!q.isEmpty()) {
                            size++;
                            Pair<Integer, Integer> now = q.remove();
                            for (int dy = -1; dy <= 1; dy++) {
                                for (int dx = -1; dx <= 1; dx++) {
                                    int nx = now.getLeft() + dx;
                                    int ny = now.getRight() + dy;
                                    if (nx >= 0 && ny >= 0 && nx < maxx && ny < maxy && !taken[ny][nx] && g[ny][nx] > 0) {
                                        taken[ny][nx] = true;
                                        q.add(Pair.of(nx, ny));
                                    }
                                }
                            }
                        }
                        if (size > biggestBlob) {
                            biggestBlob = size;
                            biggestSecond = seconds;
                            for (int[] ints : g) {
                                for (int anInt : ints) {
                                    System.out.print(anInt > 0 ? anInt : ".");
                                }
                                System.out.println();
                            }
                            System.out.println("biggestBlob = " + biggestBlob);
                            System.out.println("seconds = " + seconds);
                        }
                    }
                }
            }
        }

        return biggestSecond;
    }
}
