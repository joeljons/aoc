package se.timotej.advent.y2018;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class Advent15b {

    int[] dx = {0, -1, 1, 0}; // up, vänster, höger, ner
    int[] dy = {-1, 0, 0, 1};

    public static void main(String[] args) throws IOException {
        String svar = new Advent15b().calc(Online.get());
        System.out.println("svar = " + svar);
//        Online.submit(svar);
    }

    int MAXX, MAXY;
    int elveAttack = 4;

    private String calc(List<String> strs) {
        while (true) {
            String svar = calc2(strs);
            if (svar != null) {
                return svar;
            }
            System.out.println("elveAttack = " + elveAttack);
            elveAttack++;
        }
    }

    private String calc2(List<String> strs) {
        Unit[][] g = new Unit[strs.size()][strs.get(0).length()];
        for (int y = 0; y < strs.size(); y++) {
            String str = strs.get(y);
            for (int x = 0; x < str.length(); x++) {
                char c = str.charAt(x);
                g[y][x] = new Unit(c, x, y);
            }
        }
        MAXY = g.length;
        MAXX = g[0].length;
        int[][] movez = new int[MAXY][MAXX];
        Pair<Integer, Integer>[][] from = new Pair[MAXY][MAXX];
        int roundCompleted = 0;
        while (true) {
            List<Unit> moveOrder = new ArrayList<>();
            for (int y = 0; y < MAXY; y++) {
                for (int x = 0; x < MAXX; x++) {
                    if (g[y][x].isFigur()) {
                        moveOrder.add(g[y][x]);
                    }
                }
            }
            for (Unit unit : moveOrder) {
                int sumHp = isDone(g);
                if (sumHp != -1) {
                    return String.valueOf(roundCompleted * sumHp);
                }

                boolean hasCloseEnemy = false;
                for (int dir = 0; dir < 4; dir++) {
                    if (unit.hasEnemy(g[unit.y + dy[dir]][unit.x + dx[dir]])) {
                        hasCloseEnemy = true;
                    }
                }

                if (!hasCloseEnemy) {
                    walk(g, movez, from, unit);
                }

                //Attack
                Unit target = null;
                for (int dir = 0; dir < 4; dir++) {
                    Unit other = g[unit.y + dy[dir]][unit.x + dx[dir]];
                    if (unit.hasEnemy(other)) {
                        if (target == null) {
                            target = other;
                        } else if (other.hp < target.hp) {
                            target = other;
                        }
                    }
                }
                if (target != null) {
                    if (target.c == 'E') {
                        target.hp -= 3;
                    } else {
                        target.hp -= elveAttack;
                    }
                    if (target.hp <= 0) {
                        if (target.c == 'E') {
                            return null;
                        }
                        target.c = '.';
                    }
                }

            }

            roundCompleted++;
            System.out.println("After " + roundCompleted + " rounds:");
            debug(g);
        }
    }

    private void debug(Unit[][] g) {
        for (int y = 0; y < MAXY; y++) {
            for (int x = 0; x < MAXX; x++) {
                System.out.print(g[y][x].c);
            }
            System.out.println();
        }
        System.out.println();
    }

    private void walk(Unit[][] g, int[][] movez, Pair<Integer, Integer>[][] from, Unit unit) {
        for (int[] ints : movez) {
            Arrays.fill(ints, -1);
        }
        movez[unit.y][unit.x] = 0;
        Queue<Pair<Integer, Integer>> q = new ArrayDeque<>();
        q.add(Pair.of(unit.y, unit.x));
        from[unit.y][unit.x] = null;
        while (!q.isEmpty()) {
            Pair<Integer, Integer> nu = q.remove();
            for (int dir = 0; dir < 4; dir++) {
                int nyy = nu.getLeft() + dy[dir];
                int nyx = nu.getRight() + dx[dir];
                if (unit.hasEnemy(g[nyy][nyx])) {
                    Pair<Integer, Integer> last = nu;
                    while (from[nu.getLeft()][nu.getRight()] != null) {
                        last = nu;
                        nu = from[nu.getLeft()][nu.getRight()];
                    }
                    int lasty = unit.y;
                    int lastx = unit.x;
                    unit.y = last.getLeft();
                    unit.x = last.getRight();
                    Unit goTo = g[unit.y][unit.x];
                    goTo.y = lasty;
                    goTo.x = lastx;
                    g[unit.y][unit.x] = unit;
                    g[goTo.y][goTo.x] = goTo;
                    return;
                }
                if (movez[nyy][nyx] == -1 && g[nyy][nyx].c == '.') {
                    movez[nyy][nyx] = movez[nu.getLeft()][nu.getRight()] + 1;
                    from[nyy][nyx] = nu;
                    q.add(Pair.of(nyy, nyx));
                }
            }
        }
    }

    private int isDone(Unit[][] g) {
        char lastFigur = 0;
        int sumHp = 0;
        for (int y = 0; y < MAXY; y++) {
            for (int x = 0; x < MAXX; x++) {
                if (g[y][x].isFigur()) {
                    if (lastFigur == 0) {
                        lastFigur = g[y][x].c;
                    } else if (lastFigur != g[y][x].c) {
                        return -1;
                    }
                    sumHp += g[y][x].hp;
                }
            }
        }
        return sumHp;
    }

    private class Unit {
        char c;
        int x, y;
        int hp = 200;

        public Unit(char c, int x, int y) {
            this.c = c;
            this.x = x;
            this.y = y;
        }

        public boolean isFigur() {
            return c == 'G' || c == 'E';
        }


        public boolean hasEnemy(Unit other) {
            return (c == 'G' && other.c == 'E')
                    || (c == 'E' && other.c == 'G');

        }
    }
}
