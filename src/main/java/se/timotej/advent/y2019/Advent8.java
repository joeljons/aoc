package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent8 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent8().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int svar = 0;

        String str = strs.get(0);

        int fewestZeroes = Integer.MAX_VALUE;
        for (int i = 0; i < str.length() / (25 * 6); i++) {
            Map<Character, Integer> g = new HashMap<>();
            for (int j = 0; j < (25 * 6); j++) {
                g.put(str.charAt((25 * 6) * i + j), g.getOrDefault(str.charAt((25 * 6) * i + j), 0) + 1);
            }
            if (g.get('0') < fewestZeroes) {
                fewestZeroes = g.get('0');
                svar = g.get('1') * g.get('2');
            }

        }

        return svar;
    }


}
