package se.timotej.advent.y2019;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

public class Advent15b {

    public static void main(String[] args) throws IOException, InterruptedException {
        long start = System.nanoTime();
        long svar = new Advent15b().calc(Online.get());
        long duration = (System.nanoTime() - start) / 1000000;
        System.out.println("duration = " + duration);
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dy = new int[]{-1, 1, 0, 0};
    int[] dx = new int[]{0, 0, -1, 1};

    private long calc(List<String> strs) {
        Map<Pair<Integer, Integer>, Integer> g = new HashMap<>();
        Program prog = new Program(strs.get(0));
        g.put(Pair.of(0, 0), 1);
        int x = 0;
        int y = 0;
        int oxygenX = 0;
        int oxygenY = 0;
        boolean didSomething = true;
        while (didSomething) {
            didSomething = false;
            for (int dir = 0; dir < 4; dir++) {
                int nyx = x + dx[dir];
                int nyy = y + dy[dir];
                if (!g.containsKey(Pair.of(nyx, nyy))) {
                    prog.input.add((long) dir + 1);
                    prog.run();
                    int status = prog.output.remove().intValue();
                    g.put(Pair.of(nyx, nyy), status);
                    if (status != 0) {
                        x = nyx;
                        y = nyy;
                    }
                    if (status == 2) {
                        oxygenX = x;
                        oxygenY = y;
                    }
                    didSomething = true;
                    break;
                }
            }
            if (didSomething) {
                continue;
            }
            for (Map.Entry<Pair<Integer, Integer>, Integer> pairIntegerEntry : g.entrySet()) {
                int tx = pairIntegerEntry.getKey().getKey();
                int ty = pairIntegerEntry.getKey().getValue();
                int status = pairIntegerEntry.getValue();
                if (status != 0) {
                    for (int dir = 0; dir < 4; dir++) {
                        int nyx = tx + dx[dir];
                        int nyy = ty + dy[dir];
                        if (!g.containsKey(Pair.of(nyx, nyy))) {
                            List<Integer> way = findWay(g, x, y, tx, ty);
                            for (Integer mydir : way) {
                                prog.input.add((long) mydir + 1);
                                prog.run();
                                int moveStatus = prog.output.remove().intValue();
                                if (moveStatus == 0) {
                                    throw new RuntimeException("BAM");
                                }
                            }
                            x = tx;
                            y = ty;
                            didSomething = true;
                            break;
                        }
                    }
                }
                if (didSomething) {
                    break;
                }
            }
        }
        Map<Pair<Integer, Integer>, Integer> o2 = new HashMap<>();
        o2.put(Pair.of(oxygenX, oxygenY), 0);
        for (int i = 0; ; i++) {
            int finali = i;
            List<Map.Entry<Pair<Integer, Integer>, Integer>> collect = o2.entrySet().stream().filter(e -> e.getValue() == finali).collect(Collectors.toList());
            if (collect.isEmpty()) {
                return i - 1;
            }
            for (Map.Entry<Pair<Integer, Integer>, Integer> pairIntegerEntry : collect) {
                int tx = pairIntegerEntry.getKey().getKey();
                int ty = pairIntegerEntry.getKey().getValue();
                for (int dir = 0; dir < 4; dir++) {
                    int nyx = tx + dx[dir];
                    int nyy = ty + dy[dir];
                    if (!o2.containsKey(Pair.of(nyx, nyy)) && g.get(Pair.of(nyx, nyy)) != 0) {
                        o2.put(Pair.of(nyx, nyy), i + 1);
                    }
                }
            }
        }
    }

    private List<Integer> findWay(Map<Pair<Integer, Integer>, Integer> g, int x, int y, int targetX, int targetY) {
        Queue<Pair<Integer, Integer>> q = new ArrayDeque<>();
        Map<Pair<Integer, Integer>, Integer> fromDir = new HashMap<>();
        q.add(Pair.of(x, y));
        fromDir.put(Pair.of(x, y), -1);
        while (!q.isEmpty()) {
            Pair<Integer, Integer> now = q.remove();
            int nowX = now.getKey();
            int nowY = now.getValue();
            for (int dir = 0; dir < 4; dir++) {
                int nyx = nowX + dx[dir];
                int nyy = nowY + dy[dir];
                Pair<Integer, Integer> ny = Pair.of(nyx, nyy);
                if (g.containsKey(ny) && g.get(ny) != 0) {
                    if (!fromDir.containsKey(ny)) {
                        fromDir.put(ny, dir);
                        q.add(ny);
                    }
                }
            }
        }
        int nowX = targetX;
        int nowY = targetY;
        List<Integer> way = new ArrayList<>();
        while (nowX != x || nowY != y) {
            int dir = fromDir.get(Pair.of(nowX, nowY));
            way.add(dir);
            nowX -= dx[dir];
            nowY -= dy[dir];
        }
        Collections.reverse(way);
        return way;
    }
}
