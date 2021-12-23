package se.timotej.advent.y2021;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;

public class Advent23b {

    public static void main(String[] args) throws IOException {
        var svar = new Advent23b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};
    int[] costs = new int[]{1, 10, 100, 1000};
    int[] wantedX = new int[]{3, 3, 3, 3, 5, 5, 5, 5, 7, 7, 7, 7, 9, 9, 9, 9};

    private int calc(List<String> strs) {
        strs.add(3, "  #D#C#B#A#");
        strs.add(4, "  #D#B#A#C#");
        Map<State, Integer> bestScore = new TreeMap<>();
        int maxy = strs.size();
        int maxx = strs.get(0).length();
        char[][] world = new char[maxy][];
        State start = new State();
        for (int y = 0; y < maxy; y++) {
            String str = strs.get(y);
            while (str.length() < maxx) {
                str += " ";
            }
            world[y] = str.toCharArray();
            for (int x = 0; x < maxx; x++) {
                if (Character.isLetter(world[y][x])) {
                    int c = world[y][x] - 'A';
                    c *= 4;
                    while (start.pos[c][0] != 0) {
                        c++;
                    }
                    start.pos[c][0] = y;
                    start.pos[c][1] = x;
                    world[y][x] = '.';
                }
            }
        }
        start.fix();
        Queue<Pair<Integer, State>> q = new PriorityQueue<>();
        q.add(Pair.of(0, start));
        int lastScore = 0;
        while (!q.isEmpty()) {
            Pair<Integer, State> entry = q.poll();
            if (entry.getLeft() != lastScore) {
                System.out.println(q.size() + " " + bestScore.size() + " " + entry);
                lastScore = entry.getLeft();
            }
            State nu = entry.getRight();
            boolean won = true;
            for (int c = 0; c < 16; c++) {
                world[nu.pos[c][0]][nu.pos[c][1]] = (char) ((c / 4) + 'A');
                if (nu.pos[c][1] != wantedX[c] || nu.pos[c][0] < 2) {
                    won = false;
                }
            }
            if (won) {
                return entry.getLeft();
            }
            for (int c = 0; c < 16; c++) {
                if (nu.moves[c] >= 2) {
                    continue;
                }
                Queue<Triple<Integer, Integer, Integer>> q2 = new ArrayDeque<>();
                q2.add(Triple.of(nu.pos[c][0], nu.pos[c][1], entry.getLeft()));
                while (!q2.isEmpty()) {
                    Triple<Integer, Integer, Integer> nuPos = q2.poll();
                    for (int dir = 0; dir < 4; dir++) {
                        int nyx = nuPos.getMiddle() + dx[dir];
                        int nyy = nuPos.getLeft() + dy[dir];
                        if (!Character.isLetter(world[nyy][nyx]) && world[nyy][nyx] != '#' && world[nyy][nyx] != (char) (c + '0')) {
                            world[nyy][nyx] = (char) (c + '0');
                            int nyCost = nuPos.getRight() + costs[c / 4];
                            q2.add(Triple.of(nyy, nyx, nyCost));
                            if ((nu.pos[c][0] == 1 && nyy > 1 && wantedX[c] == nyx) || (nu.pos[c][0] > 1 && nyy == 1)) {
                                if (nyy == 1 && (nyx == 3 || nyx == 5 || nyx == 7 || nyx == 9)) {
                                    continue;
                                }
                                State nextState = new State(nu);
                                nextState.pos[c][0] = nyy;
                                nextState.pos[c][1] = nyx;
                                nextState.moves[c]++;
                                nextState.fix();
                                if (nyCost < bestScore.getOrDefault(nextState, Integer.MAX_VALUE)) {
                                    bestScore.put(nextState, nyCost);
                                    q.add(Pair.of(nyCost, nextState));
                                }
                            }
                        }
                    }
                }

                world[nu.pos[c][0]][nu.pos[c][1]] = (char) ((c / 4) + 'A');
            }

            for (int y = 0; y < maxy; y++) {
                for (int x = 0; x < maxx; x++) {
                    if (Character.isLetter(world[y][x]) || Character.isDigit(world[y][x])) {
                        world[y][x] = '.';
                    }
                }
            }
        }
        return 0;
    }

    private static class State implements Comparable<State> {
        int[] moves = new int[16];
        int[][] pos = new int[16][2];

        public State() {
        }

        public State(State nu) {
            for (int c = 0; c < 16; c++) {
                pos[c][0] = nu.pos[c][0];
                pos[c][1] = nu.pos[c][1];
                moves[c] = nu.moves[c];
            }
        }

        @Override
        public int compareTo(State o) {
            int cmp = Arrays.compare(moves, o.moves);
            if (cmp != 0) {
                return cmp;
            }
            for (int i = 0; i < 16; i++) {
                cmp = Arrays.compare(pos[i], o.pos[i]);
                if (cmp != 0) {
                    return cmp;
                }
            }
            return 0;
        }

        @Override
        public String toString() {
            return "State{" +
                    "moves=" + Arrays.toString(moves) +
                    ", pos=" + Arrays.toString(pos) +
                    '}';
        }

        public void fix() {
            for (int e = 0; e < 4; e++) {
                for (int e1 = 0; e1 < 4; e1++) {
                    for (int e2 = e1 + 1; e2 < 4; e2++) {
                        int E1 = 4 * e + e1;
                        int E2 = 4 * e + e2;
                        if (pos[E1][0] > pos[E2][0] || (pos[E1][0] == pos[E2][0] && pos[E1][1] > pos[E2][1])) {
                            int[] tmp = pos[E1];
                            pos[E1] = pos[E2];
                            pos[E2] = tmp;
                            int tmp2 = moves[E1];
                            moves[E1] = moves[E2];
                            moves[E2] = tmp2;
                        }
                    }
                }

            }
        }
    }
}
