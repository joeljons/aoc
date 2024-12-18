package se.timotej.advent.y2024;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class Advent18b {

    public static void main(String[] args) throws IOException {
        String svar = new Advent18b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};

    private String calc(List<String> strs) {
        char[][] g = new char[71][71];
        for (String str : strs) {
            List<Integer> ints = Util.findAllInts(str);
            g[ints.get(1)][ints.get(0)] = '#';
            int[][] dist = new int[71][71];

            Queue<Pair<Integer, Integer>> q = new ArrayDeque<>();
            q.add(Pair.of(0, 0));
            while (!q.isEmpty()) {
                Pair<Integer, Integer> now = q.remove();
                int x = now.getLeft();
                int y = now.getRight();
                for (int dir = 0; dir < 4; dir++) {
                    int newX = x + dx[dir];
                    int newY = y + dy[dir];
                    if (newX >= 0 && newX <= 70 && newY >= 0 && newY <= 70 && g[newX][newY] != '#' && dist[newY][newX] == 0) {
                        dist[newY][newX] = dist[y][x] + 1;
                        q.add(Pair.of(newX, newY));
                    }
                }
            }
            if (dist[70][70] == 0) {
                return str;
            }
        }

        return "";
    }
}