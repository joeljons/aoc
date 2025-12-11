package se.timotej.advent.y2025;

import java.io.IOException;
import java.util.*;

public class Advent11 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent11().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        Map<String, List<String>> g = new HashMap<>();
        for (String str : strs) {
            String[] connections = str.split(":? ");
            g.put(connections[0], Arrays.asList(connections).subList(1, connections.length));
        }
        return getWays(g, "you", "out");
    }

    private static Long getWays(Map<String, List<String>> g, String start, String end) {
        Map<String, Long> ways = new HashMap<>();
        Queue<String> q = new LinkedList<>();
        Set<String> seen = new HashSet<>();
        ways.put(start, 1L);
        q.add(start);
        while (!q.isEmpty()) {
            String now = q.remove();
            List<String> dests = g.get(now);
            if (dests != null) {
                for (String dest : dests) {
                    ways.put(dest, ways.getOrDefault(dest, 0L) + ways.get(now));
                    if (seen.add(dest)) {
                        q.add(dest);
                    }
                }
            }

        }
        return ways.get(end);
    }
}
