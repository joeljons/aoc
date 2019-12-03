package se.timotej.advent.y2017;

import java.io.IOException;
import java.util.List;

public class Advent19b {
    public static void main(String[] args) throws IOException {
        //new Advent21().calc(Collections.singletonList("3"));
        new Advent19b().calc(Online.get(19));
    }

    private void calc(List<String> strs) {
        int x = strs.get(0).indexOf("|");
        int y = 0;
        int dy = 1;
        int dx = 0;
        int step = 0;
        while (true) {
            step++;
            //System.out.println("step = " + step);
//            System.out.printf("%d\t%d%n", x, y);
            if (dy != 0) {
                y += dy;
                if (y < 0 || y > strs.size()) {
                    break;
                }
                char c = strs.get(y).charAt(x);
                if (c == ' ') {
                    break;
                }
                if (Character.isLetter(c)) {
//                    svar += c;
//                    System.out.println("svar = " + svar);
                }
                if (c == '+') {
                    dy = 0;
                    if (x > 0 && meLike(strs.get(y).charAt(x - 1), '-')) {
                        dx = -1;
                    } else if (x < strs.get(y).length() - 1 && meLike(strs.get(y).charAt(x + 1), '-')) {
                        dx = 1;
                    } else {
                        throw new RuntimeException();
                    }
                }
            } else
            if (dx != 0) {
                x += dx;
                if (x < 0 || x > strs.get(y).length()) {
                    break;
                }
                char c = strs.get(y).charAt(x);
                if (c == ' ') {
                    break;
                }
                if (Character.isLetter(c)) {
//                    svar += c;
//                    System.out.println("svar = " + svar);
                }
                if (c == '+') {
                    dx = 0;
                    if (y > 0 && meLike(strs.get(y - 1).charAt(x), '|')) {
                        dy = -1;
                    } else if (y < strs.size() - 1 && meLike(strs.get(y + 1).charAt(x), '|')) {
                        dy = 1;
                    } else {
                        throw new RuntimeException();
                    }
                }
            }
        }
        Online.submit(19, 2, step);
    }

    private boolean meLike(char actual, char wanted) {
        return actual == wanted || Character.isLetter(actual);
    }

}