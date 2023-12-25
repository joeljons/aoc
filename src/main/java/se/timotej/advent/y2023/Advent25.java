package se.timotej.advent.y2023;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Advent25 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent25().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private final List<Pair<String, String>> wires = new ArrayList<>();
    private final Multimap<String, String> wireConnections = ArrayListMultimap.create();

    private int calc(List<String> strs) {
        for (String str : strs) {
            String[] split = str.split("[: ]+");
            for (int i = 1; i < split.length; i++) {
                wires.add(Pair.of(split[0], split[i]));
            }
        }

        for (Pair<String, String> wire : wires) {
            wireConnections.put(wire.getLeft(), wire.getRight());
            wireConnections.put(wire.getRight(), wire.getLeft());
        }

        return IntStream.range(0, wires.size()).parallel().flatMap(i -> {
            for (int j = i + 1; j < wires.size(); j++) {
                for (int k = j + 1; k < wires.size(); k++) {
                    int svar = tryCalc(i, j, k);
                    if (svar != 0) {
                        return IntStream.of(svar);
                    }
                }
            }
            return IntStream.empty();
        }).findAny().orElse(0);
    }

    private int tryCalc(int i, int j, int k) {
        Set<String> kvar = new HashSet<>(wireConnections.keySet());
        List<Pair<String, String>> forbidden = List.of(wires.get(i), wires.get(j), wires.get(k));
        int startSize = kvar.size();
        fyll(forbidden, kvar);
        int a = kvar.size();
        int b = startSize - a;
        if (!kvar.isEmpty()) {
            fyll(forbidden, kvar);
            if (kvar.isEmpty()) {
                return a * b;
            }
        }
        return 0;
    }

    private void fyll(List<Pair<String, String>> forbidden, Set<String> kvar) {
        Queue<String> q = new ArrayDeque<>();
        String startPos = kvar.iterator().next();
        q.add(startPos);
        kvar.remove(startPos);
        while (!q.isEmpty()) {
            String now = q.remove();
            for (String dest : wireConnections.get(now)) {
                if (kvar.contains(dest)
                        && !forbidden.contains(Pair.of(now, dest))
                        && !forbidden.contains(Pair.of(dest, now))) {
                    kvar.remove(dest);
                    q.add(dest);
                }
            }
        }
    }
}
