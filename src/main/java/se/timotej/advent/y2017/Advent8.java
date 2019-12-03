package se.timotej.advent.y2017;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Advent8 {
    public static void main(String[] args) throws IOException {
        new Advent8().calc(Online.get(8));
    }

    private void calc(List<String> str) {
        Map<String, Integer> g = new HashMap<>();
        int maxmax = 0;
        for (String s : str) {
            String[] split = s.split(" ");
            String register = split[0];
            String incDec = split[1];
            int modValue = Integer.parseInt(split[2]);
            String checkReg = split[4];
            String cmp = split[5];
            int checkValue = Integer.parseInt(split[6]);
            if (cmp(g.getOrDefault(checkReg, 0), cmp, checkValue)) {
                if (incDec.equals("dec")) {
                    modValue *= -1;
                }
                g.put(register, g.getOrDefault(register, 0) + modValue);
            }
            Optional<Integer> biggest = g.values().stream().max(Integer::compare);
            if (biggest.isPresent()) {
                maxmax = Math.max(biggest.get(), maxmax);
            }
        }
        Integer max = g.values().stream().max(Integer::compare).get();
        System.out.println("max = " + max);
        System.out.println("maxmax = " + maxmax);
    }

    private boolean cmp(int left, String op, int right) {
        if (op.equals("<")) {
            return left < right;
        }
        if (op.equals("<=")) {
            return left <= right;
        }
        if (op.equals("==")) {
            return left == right;
        }
        if (op.equals(">=")) {
            return left >= right;
        }
        if (op.equals(">")) {
            return left > right;
        }
        if (op.equals("!=")) {
            return left != right;
        }
        throw new RuntimeException("Invalid operator: " + op);
    }
}
