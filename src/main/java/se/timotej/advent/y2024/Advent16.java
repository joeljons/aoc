package se.timotej.advent.y2024;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.*;

public class Advent16 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent16().calc(Online.get(0));
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};

    private long calc(List<String> strs) {
        char[][] g = Util.charMatrix(strs);
        int maxy = g.length;
        int maxx = g[0].length;
        Set<Entry> seen = new HashSet<>();
        PriorityQueue<Pair<Integer, Entry>> q = new PriorityQueue<>();
        q.add(Pair.of(0, new Entry(1, maxy - 2, 1)));
        while (!q.isEmpty()) {
            Pair<Integer, Entry> front = q.remove();
            Entry now = front.getRight();
            seen.add(now);
            int score = front.getLeft();
            g[now.y][now.x] = '@';
            if (now.x == maxx - 2 && now.y == 1) {
                for (char[] chars : g) {
                    System.out.println(new String(chars));
                }
                return score;
            }
            int nx = now.x + dx[now.dir];
            int ny = now.y + dy[now.dir];
            if (g[ny][nx] != '#') {
                Entry e = new Entry(nx, ny, now.dir);
                if (!seen.contains(e)) {
                    q.add(Pair.of(score + 1, e));
                }
            }
            Entry e = new Entry(now.x, now.y, (now.dir + 1) % 4);
            if (!seen.contains(e)) {
                q.add(Pair.of(score + 1000, e));
            }
            e = new Entry(now.x, now.y, (now.dir + 3) % 4);
            if (!seen.contains(e)) {
                q.add(Pair.of(score + 1000, e));
            }
        }

        return 0;
    }

    record Entry(int x, int y, int dir) implements Comparable<Entry> {
        @Override
        public int compareTo(Entry o) {
            return Comparator.comparingInt(Entry::x).thenComparingInt(Entry::y).thenComparing(Entry::dir).compare(this, o);
        }
    }
}
