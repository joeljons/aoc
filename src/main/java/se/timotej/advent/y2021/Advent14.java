package se.timotej.advent.y2021;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent14 {

    public static void main(String[] args) throws IOException {
        var svar = new Advent14().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        String now = strs.get(0);
        Map<String, String> map = new HashMap<>();
        for (String str : strs.subList(2, strs.size())) {
            String[] split = str.split(" -> ");
            map.put(split[0], split[1]);
        }
        for (int step = 0; step < 10; step++) {
            StringBuilder next = new StringBuilder();
            for (int i = 0; i < now.length() - 1; i++) {
                next.append(now.charAt(i));
                next.append(map.get(now.charAt(i) + "" + now.charAt(i + 1)));
            }
            next.append(now.charAt(now.length() - 1));
            now = next.toString();
        }
        Map<Character, Integer> antal = new HashMap<>();
        for (int i = 0; i < now.length(); i++) {
            char c = now.charAt(i);
            antal.put(c, antal.getOrDefault(c, 0) + 1);
        }
        int min = Integer.MAX_VALUE;
        int max = 0;
        for (Integer value : antal.values()) {
            max = Math.max(value, max);
            min = Math.min(value, min);
        }
        return max - min;
    }

}
