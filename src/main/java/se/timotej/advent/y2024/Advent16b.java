package se.timotej.advent.y2024;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.io.IOException;
import java.util.*;

public class Advent16b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent16b().calc(Online.get(16));
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};
    int maxScore = Integer.MAX_VALUE;

    private long calc(List<String> strs) {
        char[][] g = Util.charMatrix(strs);
        int maxy = g.length;
        int maxx = g[0].length;
        long lastSvar = 0;
        boolean improved;
        do {
            improved = false;
            long svar = doRun(g, maxx, maxy);
            if (svar != lastSvar) {
                lastSvar = svar;
                improved = true;
                System.out.println("svar = " + svar);
            }
        } while (improved);
        return lastSvar;
    }

    private int doRun(char[][] g, int maxx, int maxy) {
        Map<Entry, Entry> seenFrom = new HashMap<>();
        PriorityQueue<Triple<Integer, Integer, Pair<Entry, Entry>>> q = new PriorityQueue<>();
        q.add(Triple.of(0, 0, Pair.of(new Entry(1, maxy - 2, 1), null)));
        while (!q.isEmpty()) {
            Triple<Integer, Integer, Pair<Entry, Entry>> front = q.remove();
            Entry now = front.getRight().getLeft();
            if (!seenFrom.containsKey(now)) {
                seenFrom.put(now, front.getRight().getRight());
            }
            int score = front.getLeft();
            int newCount = front.getMiddle();
            if (now.x == maxx - 2 && now.y == 1) {
                if (maxScore == Integer.MAX_VALUE) {
                    maxScore = score;
                }
                break;
            }
            if (score > maxScore) {
                break;
            }
            int nx = now.x + dx[now.dir];
            int ny = now.y + dy[now.dir];
            if (g[ny][nx] != '#') {
                Entry e = new Entry(nx, ny, now.dir);
                if (!seenFrom.containsKey(e)) {
                    q.add(Triple.of(score + 1, newCount - (g[ny][nx] == '@' ? 0 : 1), Pair.of(e, now)));
                }
            }
            Entry e = new Entry(now.x, now.y, (now.dir + 1) % 4);
            if (!seenFrom.containsKey(e)) {
                q.add(Triple.of(score + 1000, newCount, Pair.of(e, now)));
            }
            e = new Entry(now.x, now.y, (now.dir + 3) % 4);
            if (!seenFrom.containsKey(e)) {
                q.add(Triple.of(score + 1000, newCount, Pair.of(e, now)));
            }
        }
        Queue<Entry> q2 = new ArrayDeque<>();
        for (int dir = 0; dir < 4; dir++) {
            q2.add(new Entry(maxx - 2, 1, dir));
        }
        Set<Entry> wentBackHere = new HashSet<>();
        while (!q2.isEmpty()) {
            Entry now = q2.remove();
            g[now.y][now.x] = '@';
            if (wentBackHere.add(now) && seenFrom.get(now) != null) {
                q2.add(seenFrom.get(now));
            }
        }

        int svar = 0;
        for (char[] chars : g) {
            for (char aChar : chars) {
                if (aChar == '@') {
                    svar++;
                }
            }
            System.out.println(new String(chars));
        }
        return svar;
    }

    record Entry(int x, int y, int dir) implements Comparable<Entry> {
        @Override
        public int compareTo(Entry o) {
            return Comparator.comparingInt(Entry::x).thenComparingInt(Entry::y).thenComparing(Entry::dir).compare(this, o);
        }
    }
}
