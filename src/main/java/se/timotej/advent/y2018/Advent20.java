package se.timotej.advent.y2018;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Advent20 {

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        String svar = new Advent20().calc(Online.get());
        long duration = System.currentTimeMillis()-start;
        System.out.println("duration = " + duration);
        System.out.println("svar = " + svar);
//        Online.submit(svar);
    }

    Set<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> g = new HashSet<>();

    char[] str;
    Set<Pair<Integer, Pair<Integer, Integer>>> beenHere = new HashSet<>();
    Queue<Pair<Integer, Pair<Integer, Integer>>> goQ = new ArrayDeque<>();
    Map<Integer, List<Integer>> startPosses = new HashMap<>();
    Map<Integer, Integer> slutParenteses = new HashMap<>();

    private String calc(List<String> strs) {
        str = strs.get(0).substring(1, strs.get(0).length() - 1).toCharArray();
        goQ.add(Pair.of(0, Pair.of(0, 0)));
        while (!goQ.isEmpty()) {
            Pair<Integer, Pair<Integer, Integer>> now = goQ.remove();
            if (beenHere.contains(now)) {
                continue;
            }
            beenHere.add(now);
            int pos = now.getLeft();
            Pair<Integer, Integer> here = now.getRight();
            int x = here.getLeft();
            int y = here.getRight();

            if (pos >= str.length) {
                continue;
            }
            Pair<Integer, Integer> next = null;
            if (str[pos] == 'N') {
                next = Pair.of(x, y - 1);
            } else if (str[pos] == 'S') {
                next = Pair.of(x, y + 1);
            } else if (str[pos] == 'E') {
                next = Pair.of(x + 1, y);
            } else if (str[pos] == 'W') {
                next = Pair.of(x - 1, y);
            }
            if (next != null) {
                g.add(Pair.of(here, next));
                g.add(Pair.of(next, here));
                goQ.add(Pair.of(pos + 1, next));
            } else {
                if (str[pos] == '(') {
                    List<Integer> starts = startPosses.computeIfAbsent(pos, this::findStarts);
                    for (Integer start : starts) {
                        goQ.add(Pair.of(start, here));
                    }
                } else if (str[pos] == ')') {
                    goQ.add(Pair.of(pos + 1, here));
                } else if (str[pos] == '|') {
                    goQ.add(Pair.of(slutParenteses.computeIfAbsent(pos, this::getNextSlutParentes), here));
                }
            }
        }

        Map<Pair<Integer, Integer>, List<Pair<Integer, Integer>>> doors = new HashMap<>();
        for (Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> pairPairPair : g) {
            Pair<Integer, Integer> from = pairPairPair.getLeft();
            Pair<Integer, Integer> to = pairPairPair.getRight();
            doors.computeIfAbsent(from, key -> new ArrayList<>()).add(to);
        }

        Map<Pair<Integer, Integer>, Integer> dist = new HashMap<>();
        dist.put(Pair.of(0, 0), 0);
        Queue<Pair<Integer, Integer>> q = new ArrayDeque<>();
        q.add(Pair.of(0, 0));
        while (!q.isEmpty()) {
            Pair<Integer, Integer> now = q.remove();
            for (Pair<Integer, Integer> next : doors.get(now)) {
                if (!dist.containsKey(next)) {
                    dist.put(next, dist.get(now) + 1);
                    q.add(next);
                }
            }
        }
        Integer svar = dist.values().stream().max(Integer::compare).get();

        return String.valueOf(svar);
    }

    private int getNextSlutParentes(int pos) {
        int depth = 0;
        for (int p = pos + 1; p < str.length; p++) {
            if (str[p] == '(') {
                depth++;
            }
            if (str[p] == ')') {
                depth--;
                if (depth < 0) {
                    return p + 1;
                }
            }
        }
        throw new RuntimeException("hittar inte ) från "+ pos);
    }

    private List<Integer> findStarts(int pos) {
        int depth = 0;
        List<Integer> starts = new ArrayList<>();
        starts.add(pos + 1);
        for (int p = pos + 1; depth >= 0; p++) {
            if (str[p] == '|' && depth == 0) {
                starts.add(p + 1);
            }
            if (str[p] == '(') {
                depth++;
            }
            if (str[p] == ')') {
                depth--;
            }
        }
        return starts;
    }


    private void go(int pos, int x, int y) {
        if (str.length >= pos) {
            return;
        }
        Pair<Integer, Integer> here = Pair.of(x, y);
        Pair<Integer, Pair<Integer, Integer>> key = Pair.of(pos, here);
        if (beenHere.contains(key)) {
            return;
        }
        beenHere.add(key);
        if (str[pos] == '(') {
            go(pos + 1, x, y);
        }

    }

}