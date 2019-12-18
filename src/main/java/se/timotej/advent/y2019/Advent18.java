package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

public class Advent18 {

    public static void main(String[] args) throws IOException {
        long start = System.nanoTime();
        long svar = new Advent18().calc(Online.get());
        long duration = (System.nanoTime() - start) / 1000000;
        System.out.println("duration = " + duration);
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};

    private long calc(List<String> strs) {
        int maxx = strs.get(0).length();
        int maxy = strs.size();
        char[][] g = new char[maxy][];
        int keyCount = 0;
        State startState = null;
        for (int y = 0; y < strs.size(); y++) {
            g[y] = strs.get(y).toCharArray();
            for (int x = 0; x < maxx; x++) {
                if (Character.isLowerCase(g[y][x])) {
                    keyCount++;
                }
                if (g[y][x] == '@') {
                    startState = new State(x, y, 0);
                    g[y][x] = '.';
                }
            }
        }

        Queue<State> q = new ArrayDeque<>();
        Map<State, Integer> dist = new HashMap<>();
        q.add(startState);
        dist.put(startState, 0);

        while (!q.isEmpty()) {
            State nu = q.remove();
            for (int dir = 0; dir < 4; dir++) {
                int nyx = nu.x + dx[dir];
                int nyy = nu.y + dy[dir];
                char c = g[nyy][nyx];
                if (c == '.' || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z' && nu.hasKey(c))) {
                    int keysFound = nu.keysFound;
                    if(c >= 'a' && c <= 'z'){
                        keysFound |= 1 << (c - 'a');
                    }
                    State ny = new State(nyx, nyy, keysFound);
                    if (!dist.containsKey(ny)) {
                        q.add(ny);
                        dist.put(ny, dist.get(nu) + 1);
                        if(keysFound == (1<<(keyCount))-1){
                            return dist.get(nu) + 1;
                        }
                    }
                }
            }

        }
        return 0;
    }

    private class State {
        int x, y, keysFound;

        public State(int x, int y, int keysFound) {
            this.x = x;
            this.y = y;
            this.keysFound = keysFound;
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
            return x == state.x &&
                    y == state.y &&
                    keysFound == state.keysFound;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, keysFound);
        }

        public boolean hasKey(char c) {
            return (keysFound & (1 << (c - 'A'))) != 0;
        }
    }
}
