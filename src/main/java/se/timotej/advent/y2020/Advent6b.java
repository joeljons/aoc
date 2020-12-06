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
        for (List<String> group : Util.splitByDoubleNewline(strs)) {
            Map<Character, Integer> q = new HashMap<>();
            for (String str : group) {
                for (int i = 0; i < str.length(); i++) {
                    char c = str.charAt(i);
                    q.merge(c, 1, Integer::sum);
                }
            }
            for (Integer value : q.values()) {
                if (value == group.size()) {
                    sum++;
                }
            }
        }
        return sum;
    }
}
