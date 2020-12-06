package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent6b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent6b().calc(Online.get());
        System.out.println("svar = " + svar);

        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int sum = 0;
        Map<Character, Integer> q = new HashMap<>();
        int antal = 0;
        for (String str : strs) {
            if (str.isEmpty()) {
                for (Integer value : q.values()) {
                    if (value == antal) {
                        sum++;
                    }
                }
                q = new HashMap<>();
                antal = 0;
                continue;
            }
            antal++;
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                q.merge(c, 1, Integer::sum);
            }
        }
        for (Integer value : q.values()) {
            if (value == antal) {
                sum++;
            }
        }
        return sum;
    }


}
