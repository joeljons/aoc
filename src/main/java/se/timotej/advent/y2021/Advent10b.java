package se.timotej.advent.y2021;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Advent10b {

    public static void main(String[] args) throws IOException {
        var svar = new Advent10b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    Map<Character, Character> map = new HashMap<>();
    Map<Character, Integer> score = new HashMap<>();

    private long calc(List<String> strs) {
        map.put('(', ')');
        map.put('[', ']');
        map.put('{', '}');
        map.put('<', '>');
        score.put(')', 1);
        score.put(']', 2);
        score.put('}', 3);
        score.put('>', 4);
        List<Long> scores = new ArrayList<>();
        for (String str : strs) {
            Deque<Character> stack = new ArrayDeque<>();
            boolean error = false;
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (map.containsKey(c)) {
                    stack.push(map.get(c));
                } else {
                    Character expected = stack.pop();
                    if (expected != c) {
                        error = true;
                        break;
                    }
                }
            }
            if (!error) {
                long nu = 0;
                while(!stack.isEmpty()){
                    nu *= 5;
                    nu += score.get(stack.pop());
                }
                scores.add(nu);
            }
        }
        Collections.sort(scores);
        return scores.get(scores.size()/2);
    }
}
