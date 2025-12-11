package se.timotej.advent.y2025;

import java.io.IOException;
import java.util.*;

public class Advent11b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent11b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        Map<String, List<String>> g = new HashMap<>();
        Map<String, List<String>> gReverse = new HashMap<>();
        gReverse.put("svr", Collections.emptyList());
        for (String str : strs) {
            String[] connections = str.split(":? ");
            g.put(connections[0], Arrays.asList(connections).subList(1, connections.length));
            for (String dest : g.get(connections[0])) {
                gReverse.computeIfAbsent(dest, k -> new ArrayList<>()).add(connections[0]);
            }
        }
        return getWays(g, gReverse, "svr", "dac") * getWays(g, gReverse, "dac", "fft") * getWays(g, gReverse, "fft", "out")
                + getWays(g, gReverse, "svr", "fft") * getWays(g, gReverse, "fft", "dac") * getWays(g, gReverse, "dac", "out");
    }

    private static Long getWays(Map<String, List<String>> g, Map<String, List<String>> gReverse, String start, String end) {
        Map<String, Long> ways = new HashMap<>();
        Queue<String> q = new LinkedList<>();
        Set<String> seen = new HashSet<>();
        Map<String, Set<String>> arrivedFrom = new HashMap<>();
        ways.put(start, 1L);
        q.add(start);
        Map<String, Integer> readdCount = new HashMap<>();
        while (!q.isEmpty()) {
            String now = q.remove();
            if (gReverse.get(now).size() != arrivedFrom.getOrDefault(now, Collections.emptySet()).size()
                    && readdCount.merge(now, 1, Integer::sum) < 100) {
                q.add(now);
                continue;
            }
            List<String> dests = g.get(now);
            if (dests != null) {
                dests = new ArrayList<>(dests);
                for (String dest : dests) {
                    ways.put(dest, ways.getOrDefault(dest, 0L) + ways.get(now));
                    arrivedFrom.computeIfAbsent(dest, k -> new HashSet<>()).add(now);
                    if (seen.add(dest)) {
                        q.add(dest);
                    }
                }
            }
        }
        return ways.getOrDefault(end, 0L);
    }
}
