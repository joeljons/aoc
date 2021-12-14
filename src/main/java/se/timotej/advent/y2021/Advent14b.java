package se.timotej.advent.y2021;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent14b {

    public static void main(String[] args) throws IOException {
        var svar = new Advent14b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        String start = strs.get(0);
        Map<String, Long> now = new HashMap<>();
        for (int i = 0; i < start.length() - 1; i++) {
            char c = start.charAt(i);
            char c2 = start.charAt(i + 1);
            String key = c + "" + c2;
            now.put(key, now.getOrDefault(key, 0L) + 1);
        }
        Map<String, String> map = new HashMap<>();
        for (String str : strs.subList(2, strs.size())) {
            String[] split = str.split(" -> ");
            map.put(split[0], split[1]);
        }
        for (int step = 0; step < 40; step++) {
            Map<String, Long> next = new HashMap<>();
            for (Map.Entry<String, Long> entry : now.entrySet()) {
                Long count = entry.getValue();
                String key = entry.getKey();
                String mid = map.get(key);
                String key1 = key.charAt(0) + mid;
                String key2 = mid + key.charAt(1);
                next.put(key1, next.getOrDefault(key1, 0L) + count);
                next.put(key2, next.getOrDefault(key2, 0L) + count);

            }
            now = next;
        }
        Map<Character, Long> antal = new HashMap<>();
        for (Map.Entry<String, Long> entry : now.entrySet()) {
            Long count = entry.getValue();
            String key = entry.getKey();
            antal.put(key.charAt(0), antal.getOrDefault(key.charAt(0), 0L) + count);
            antal.put(key.charAt(1), antal.getOrDefault(key.charAt(1), 0L) + count);
        }
        antal.put(start.charAt(0), antal.getOrDefault(start.charAt(0), 0L) + 1);
        antal.put(start.charAt(start.length() - 1), antal.getOrDefault(start.charAt(start.length() - 1), 0L) + 1);

        long min = Long.MAX_VALUE;
        long max = 0;
        for (Long value : antal.values()) {
            max = Math.max(value, max);
            min = Math.min(value, min);
        }
        return (max - min) / 2;
    }

}
