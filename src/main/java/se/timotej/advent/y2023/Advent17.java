package se.timotej.advent.y2023;

import java.io.IOException;
import java.util.*;

public class Advent17 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent17().calc(Online.get());
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
        q.add(new State(-1, -1, -1, 0, 0, 0));
        Set<State> seen = new HashSet<>();
        while (!q.isEmpty()) {
            State now = q.remove();
            for (int dir = 0; dir < 4; dir++) {
                if (now.dir2 != -1 && (now.dir2 + 2) % 4 == dir) {
                    continue;
                }
                if (now.dir0 != -1 && now.dir0 == now.dir1 && now.dir1 == now.dir2 && now.dir2 == dir) {
                    continue;
                }
                int xx = now.x + dx[dir];
                int yy = now.y + dy[dir];
                if (xx < 0 || xx >= maxx || yy < 0 || yy >= maxy) {
                    continue;
                }
                int heatloss = now.heatloss + g[yy][xx] - '0';
                if (xx == maxx - 1 && yy == maxy - 1) {
                    return heatloss;
                }
                State next = new State(now.dir1, now.dir2, dir, xx, yy, heatloss);
                if (seen.add(next)) {
                    q.add(next);
                }
            }
        }
        return 0;
    }

    private static class State {
        int dir0;
        int dir1;
        int dir2;
        int x;
        int y;
        int heatloss;

        public int getHeatloss() {
            return heatloss;
        }

        public State(int dir0, int dir1, int dir2, int x, int y, int heatloss) {
            this.dir0 = dir0;
            this.dir1 = dir1;
            this.dir2 = dir2;
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
            return dir0 == state.dir0 && dir1 == state.dir1 && dir2 == state.dir2 && x == state.x && y == state.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(dir0, dir1, dir2, x, y);
        }
    }
}
