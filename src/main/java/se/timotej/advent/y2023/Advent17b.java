package se.timotej.advent.y2023;

import java.io.IOException;
import java.util.*;

public class Advent17b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent17b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};

    private long calc(List<String> strs) {
        char[][] g = Util.charMatrix(strs);
        int maxx = g[0].length;
        int maxy = g.length;
        PriorityQueue<State> q = new PriorityQueue<>(Comparator.comparing(State::getHeatloss));
        q.add(new State(-1, 0, 0, 0, 0));
        Set<State> seen = new HashSet<>();
        while (!q.isEmpty()) {
            State now = q.remove();
            for (int dir = 0; dir < 4; dir++) {
                if (now.lastDir != -1 && (now.lastDir + 2) % 4 == dir) {
                    continue;
                }
                if (now.lastDir != -1 && now.lastDir == dir && now.dirCount >= 10) {
                    continue;
                }
                if (now.lastDir != -1 && now.lastDir != dir && now.dirCount < 4) {
                    continue;
                }
                int xx = now.x + dx[dir];
                int yy = now.y + dy[dir];
                if (xx < 0 || xx >= maxx || yy < 0 || yy >= maxy) {
                    continue;
                }
                int heatloss = now.heatloss + g[yy][xx] - '0';
                State next = new State(dir, now.lastDir == dir ? now.dirCount + 1 : 1, xx, yy, heatloss);
                if (next.x == maxx - 1 && next.y == maxy - 1 && next.dirCount >= 4) {
                    return heatloss;
                }
                if (seen.add(next)) {
                    q.add(next);
                }
            }
        }
        return 0;
    }

    private static class State {
        int lastDir;
        int dirCount;
        int x;
        int y;
        int heatloss;

        public int getHeatloss() {
            return heatloss;
        }

        public State(int lastDir, int dirCount, int x, int y, int heatloss) {
            this.lastDir = lastDir;
            this.dirCount = dirCount;
            this.x = x;
            this.y = y;
            this.heatloss = heatloss;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            State state = (State) o;
            return lastDir == state.lastDir && dirCount == state.dirCount && x == state.x && y == state.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(lastDir, dirCount, x, y);
        }
    }
}
