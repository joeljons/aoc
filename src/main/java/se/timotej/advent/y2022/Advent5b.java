package se.timotej.advent.y2022;

import se.timotej.advent.y2021.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Advent5b {

    public static void main(String[] args) throws IOException {
        String svar = new Advent5b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private String calc(List<String> strs) {
        List<List<Character>> stacks = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            stacks.add(new ArrayList<>());
        }
        for (String str : strs) {
            if (str.contains("1")) {
                break;
            }
            for (int i = 0; i < 9; i++) {
                char c = str.charAt(1 + i * 4);
                if (c != ' ') {
                    stacks.get(i).add(0, c);
                }
            }
        }
        for (String str : strs) {
            if (!str.contains("move")) {
                continue;
            }
            List<Integer> moves = Util.findAllPositiveInts(str);
            int from = moves.get(1) - 1;
            int to = moves.get(2) - 1;
            List<Character> temp = new ArrayList<>();
            for (int i = 0; i < moves.get(0); i++) {
                temp.add(0, stacks.get(from).remove(stacks.get(from).size() - 1));
            }
            stacks.get(to).addAll(temp);
        }
        String svar = "";
        for (List<Character> stack : stacks) {
            if (!stack.isEmpty()) {
                svar += stack.get(stack.size() - 1);
            }
        }
        return svar;
    }

}
