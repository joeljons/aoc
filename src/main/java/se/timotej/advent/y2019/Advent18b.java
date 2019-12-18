package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

public class Advent18b {

    public static void main(String[] args) throws IOException {
        long start = System.nanoTime();
        long svar = new Advent18b().calc(Online.get());
        long duration = (System.nanoTime() - start) / 1000000;
        System.out.println("duration = " + duration);
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};
    char[][] g;

    private long calc(List<String> strs) {
        int maxx = strs.get(0).length();
        int maxy = strs.size();
        g = new char[maxy][];
        State startState = null;
        for (int y = 0; y < strs.size(); y++) {
            g[y] = strs.get(y).toCharArray();
            for (int x = 0; x < maxx; x++) {
                if (g[y][x] == '@') {
                    startState = new State(x, y, 0);
                    g[y][x] = '.';
                }
            }
        }
        g[startState.y][startState.x] = '#';
        g[startState.y][startState.x + 1] = '#';
        g[startState.y][startState.x - 1] = '#';
        g[startState.y + 1][startState.x] = '#';
        g[startState.y - 1][startState.x] = '#';

        return countDist(new State(startState.x - 1, startState.y - 1, 0))
                + countDist(new State(startState.x - 1, startState.y + 1, 0))
                + countDist(new State(startState.x + 1, startState.y - 1, 0))
                + countDist(new State(startState.x + 1, startState.y + 1, 0));
    }

    private long countDist(State startState) {
        startState.keysFound = getKeysNotHere(startState);
        int mostKeysFound = 0;
        long svar = 0;
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
                    if (c >= 'a' && c <= 'z') {
                        keysFound |= 1 << (c - 'a');
                    }
                    State ny = new State(nyx, nyy, keysFound);
                    if (!dist.containsKey(ny)) {
                        q.add(ny);
                        dist.put(ny, dist.get(nu) + 1);
                        if (keysFound > mostKeysFound) {
                            mostKeysFound = keysFound;
                            svar = dist.get(nu)+1;
                        }
                    }
                }
            }
        }
        return svar;
    }

    private int getKeysNotHere(State startState) {
        int keysNotHere = (1<<26)-1;
        Queue<State> q = new ArrayDeque<>();
        Set<State> visited = new HashSet<>();
        q.add(startState);
        visited.add(startState);
        while (!q.isEmpty()) {
            State nu = q.remove();
            for (int dir = 0; dir < 4; dir++) {
                int nyx = nu.x + dx[dir];
                int nyy = nu.y + dy[dir];
                char c = g[nyy][nyx];
                if (c == '.' || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                    if (c >= 'a' && c <= 'z') {
                        keysNotHere &= ~(1 << (c - 'a'));
                    }
                    State ny = new State(nyx, nyy, 0);
                    if (!visited.contains(ny)) {
                        q.add(ny);
                        visited.add(ny);
                    }
                }
            }
        }
        return keysNotHere;
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
