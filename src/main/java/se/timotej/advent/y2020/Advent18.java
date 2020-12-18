package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.List;

public class Advent18 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent18().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int i;

    private long calc(List<String> strs) {
        long sum = 0;
        for (String str : strs) {
            i = -1;
            long tal = calc(str);
            sum += tal;
        }
        return sum;
    }

    private long calc(String str) {
        long lastTal = -1;
        char lastOp = 0;
        i++;
        for (; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isDigit(c)) {
                long tal = c - '0';
                if (lastTal == -1) {
                    lastTal = tal;
                } else {
                    if (lastOp == '+') {
                        lastTal += tal;
                    } else if (lastOp == '*') {
                        lastTal *= tal;
                    }
                }
            } else if (c == '+' || c == '*') {
                lastOp = c;
            } else if (c == '(') {
                long tal = calc(str);
                if (lastTal == -1) {
                    lastTal = tal;
                } else {
                    if (lastOp == '+') {
                        lastTal += tal;
                    } else if (lastOp == '*') {
                        lastTal *= tal;
                    }
                }
            } else if (c == ')') {
                return lastTal;
            }
        }
        return lastTal;
    }
}
