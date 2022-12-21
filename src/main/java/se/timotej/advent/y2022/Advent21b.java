package se.timotej.advent.y2022;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent21b {
    public static void main(String[] args) throws IOException {
        long svar = new Advent21b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    Map<String, Yell> yells = new HashMap<>();

    private long calc(List<String> strs) {
        long diff = 0;
        long max;
        long startDiff = getDiff(strs, 0);
        for (max = 1; max==1 || diff > 0 == startDiff>0; max *= 2) {
            diff = getDiff(strs, max);
            if (diff == 0) {
                return max;
            }
        }
        max /= 2;
        long min = max / 2;
        while (true) {
            long mid = (max + min) / 2;
            diff = getDiff(strs, mid);
            if (diff == 0) {
                while (getDiff(strs, mid - 1) == 0) {
                    mid--;
                }
                return mid;
            } else if (diff > 0 == startDiff>0) {
                min = mid;
            } else {
                max = mid;
            }
        }
    }

    private long getDiff(List<String> strs, long humn) {
        yells.clear();
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
        yells.put("humn", new Yell(humn));
        long rootLeft = yells.get(yells.get("root").left).calc();
        long rootRight = yells.get(yells.get("root").right).calc();
        return rootLeft - rootRight;
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
            if (value == null) {
                long leftValue = yells.get(left).calc();
                long rightValue = yells.get(right).calc();
                value = switch (operator) {
                    case '+' -> leftValue + rightValue;
                    case '-' -> leftValue - rightValue;
                    case '*' -> leftValue * rightValue;
                    case '/' -> leftValue / rightValue;
                    default -> throw new RuntimeException("Unknown operator: " + operator);
                };
            }
            return value;
        }
    }
}