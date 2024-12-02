package se.timotej.advent.y2024;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent1bMap {

    public static void main(String[] args) throws IOException {
        int svar = new Advent1bMap().calc(Online.get(1));
        System.out.println("svar = " + svar);
        Online.submit(1, 2, svar);
    }

    private int calc(List<String> strs) {
        int svar = 0;
        Map<Integer, Integer> first = new HashMap<>();
        Map<Integer, Integer> second = new HashMap<>();
        for (String str : strs) {
            int[] line = Util.intArray(str);
            first.merge(line[0], 1, Integer::sum);
            second.merge(line[1], 1, Integer::sum);
        }
        for (Map.Entry<Integer, Integer> entry : first.entrySet()) {
            svar += second.getOrDefault(entry.getKey(), 0) * entry.getValue() * entry.getKey();
        }
        return svar;
    }
}
