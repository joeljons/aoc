package se.timotej.advent.y2016;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Advent18b {

    public static void main(String[] args) throws IOException, ExecutionException {
        int svar = new Advent18b().calc(Online.get().get(0));
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }


    private int calc(String str) {
        int count = 0;
        for (int i = 0; i < 400000; i++) {
            StringBuilder next = new StringBuilder();
            for (int j = 0; j < str.length(); j++) {
                char c = str.charAt(j);
                if (c == '.') {
                    count++;
                }

                boolean left = j > 0 && str.charAt(j - 1) == '^';
                boolean center = str.charAt(j) == '^';
                boolean right = j < str.length() - 1 && str.charAt(j + 1) == '^';
                if ((left && center && !right)
                        || (center && right && !left)
                        || (left && !center && !right)
                        || (right && !left && !center)) {
                    next.append('^');
                } else {
                    next.append('.');
                }
            }
            str = next.toString();
        }
        return count;
    }

}