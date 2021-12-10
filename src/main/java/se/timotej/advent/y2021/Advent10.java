package se.timotej.advent.y2021;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent10 {

    public static void main(String[] args) throws IOException {
        var svar = new Advent10().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    Map<Character, Character> map = new HashMap<>();
    Map<Character, Integer> errorScore = new HashMap<>();

    private int calc(List<String> strs) {
        map.put('(', ')');
        map.put('[', ']');
        map.put('{', '}');
        map.put('<', '>');
        errorScore.put(')', 3);
        errorScore.put(']', 57);
        errorScore.put('}', 1197);
        errorScore.put('>', 25137);
        int sum = 0;
        for (String str : strs) {
            Deque<Character> stack = new ArrayDeque<>();
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (map.containsKey(c)) {
                    stack.push(map.get(c));
                } else {
                    Character expected = stack.pop();
                    if (expected != c) {
                        sum += errorScore.get(c);
                        break;
                    }
                }
            }
        }
        return sum;
    }
}
