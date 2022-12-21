package se.timotej.advent.y2022;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent21 {
    public static void main(String[] args) throws IOException {
        long svar = new Advent21().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    Map<String, Yell> yells = new HashMap<>();

    private long calc(List<String> strs) {
        long svar = 0;
        for (String str : strs) {
            String[] split = str.split(" ");
            if (split.length == 2) {
                String name = split[0].replace(":", "");
                yells.put(name, new Yell(Long.valueOf(split[1])));
            } else {
                String name = split[0].replace(":", "");
                yells.put(name, new Yell(split[1], split[2].charAt(0), split[3]));
            }
        }
        svar = yells.get("root").calc();
        return svar;
    }

    private class Yell {
        Long value;
        char operator;
        String left;
        String right;

        public Yell(Long value) {
            this.value = value;
        }

        public Yell(String left, char operator, String right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        public long calc() {
            if (value != null) {
                return value;
            } else {
                long leftValue = yells.get(left).calc();
                long rightValue = yells.get(right).calc();
                if(operator == '+')value = leftValue+rightValue;
                if(operator == '*')value = leftValue*rightValue;
                if(operator == '-')value = leftValue-rightValue;
                if(operator == '/')value = leftValue/rightValue;
                return value;
            }
        }
    }
}