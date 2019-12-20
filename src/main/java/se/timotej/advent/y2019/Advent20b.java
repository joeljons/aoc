package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

import static java.lang.Character.isUpperCase;

public class Advent20b {

    public static void main(String[] args) throws IOException {
        long start = System.nanoTime();
        long svar = new Advent20b().calc(Online.get());
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
        for (int y = 0; y < strs.size(); y++) {
            g[y] = strs.get(y).toCharArray();
        }
        Map<String, Portal> portals = new HashMap<>();
        for (int x = 0; x < maxx; x++) {
            if (isUpperCase(g[0][x])) {
                String name = ("" + g[0][x]) + g[1][x];
                portals.computeIfAbsent(name, Portal::new).states.add(new State(x, 2));
            }
            if (isUpperCase(g[maxy - 2][x])) {
                String name = ("" + g[maxy - 2][x]) + g[maxy - 1][x];
                portals.computeIfAbsent(name, Portal::new).states.add(new State(x, maxy - 3));
            }
            if (isUpperCase(g[27][x])) {
                String name = ("" + g[27][x]) + g[28][x];
                portals.computeIfAbsent(name, Portal::new).states.add(new State(x, 26));
            }
            if (isUpperCase(g[80][x])) {
                String name = ("" + g[80][x]) + g[81][x];
                portals.computeIfAbsent(name, Portal::new).states.add(new State(x, 82));
            }
        }
        for (int y = 0; y < maxy; y++) {
            if (isUpperCase(g[y][0])) {
                String name = ("" + g[y][0]) + g[y][1];
                portals.computeIfAbsent(name, Portal::new).states.add(new State(2, y));
            }
            if (isUpperCase(g[y][maxx - 2])) {
                String name = ("" + g[y][maxx - 2]) + g[y][maxx - 1];
                portals.computeIfAbsent(name, Portal::new).states.add(new State(maxx - 3, y));
            }
            if (isUpperCase(g[y][27])) {
                String name = ("" + g[y][27]) + g[y][28];
                portals.computeIfAbsent(name, Portal::new).states.add(new State(26, y));
            }

            if (isUpperCase(g[y][78])) {
                String name = ("" + g[y][78]) + g[y][79];
                portals.computeIfAbsent(name, Portal::new).states.add(new State(80, y));
            }
        }

        Map<State, Portal> portalByPos = new HashMap<>();
        for (Portal portal : portals.values()) {
            for (State state : portal.states) {
                portalByPos.put(state, portal);
            }
        }

        Queue<StateLevel> q = new ArrayDeque<>();
        Map<StateLevel, Integer> dist = new HashMap<>();
        StateLevel startState = new StateLevel(portals.get("AA").states.get(0), 0);
        q.add(startState);
        dist.put(startState, 0);

        while (!q.isEmpty()) {
            StateLevel nu = q.remove();
            for (int dir = 0; dir < 4; dir++) {
                int nyx = nu.state.x + dx[dir];
                int nyy = nu.state.y + dy[dir];
                if (g[nyy][nyx] == '.') {
                    StateLevel ny = new StateLevel(new State(nyx, nyy), nu.level);
                    if (!dist.containsKey(ny)) {
                        q.add(ny);
                        dist.put(ny, dist.get(nu) + 1);
                    }
                }
            }
            Portal portal = portalByPos.get(nu.state);
            if (portal != null) {
                for (State state : portal.states) {
                    if (!nu.state.equals(state)) {
                        int deltaLevel = (state.y == 2 || state.x == 2 || state.y == maxy - 3 || state.x == maxx - 3) ? 1 : -1;
                        int newLevel = nu.level + deltaLevel;
                        StateLevel ny = new StateLevel(state, newLevel);
                        if (newLevel >= 0 && newLevel < 30 && !dist.containsKey(ny)) {
                            q.add(ny);
                            dist.put(ny, dist.get(nu) + 1);
                        }
                    }
                }
            }
        }
        return dist.get(new StateLevel(portals.get("ZZ").states.get(0), 0));
    }

    private static class Portal {
        List<State> states = new ArrayList<>();
        String name;

        public Portal(String name) {
            this.name = name;
        }
    }

    private static class StateLevel {
        State state;
        int level;

        public StateLevel(State state, int level) {
            this.state = state;
            this.level = level;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            StateLevel that = (StateLevel) o;
            return level == that.level &&
                    Objects.equals(state, that.state);
        }

        @Override
        public int hashCode() {
            return Objects.hash(state, level);
        }
    }

    private static class State {
        int x, y;

        public State(int x, int y) {
            this.x = x;
            this.y = y;
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
                    y == state.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

    }
}
