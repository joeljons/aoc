package se.timotej.advent.y2023;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent8 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent8().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;
        Map<String, Pair<String, String>> g = new HashMap<>();
        for (String str : strs.subList(2, strs.size())) {
            String[] split = str.split("[^A-Z]+");
            g.put(split[0], Pair.of(split[1], split[2]));
        }
        String now = "AAA";
        int i = 0;
        while (!now.equals("ZZZ")) {
            svar++;
            char dir = strs.get(0).charAt(i);
            if (dir == 'L') {
                now = g.get(now).getLeft();
            } else {
                now = g.get(now).getRight();
            }
            i = (i + 1) % strs.get(0).length();

        }
        return svar;
    }
}
