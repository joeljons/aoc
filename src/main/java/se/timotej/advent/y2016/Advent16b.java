package se.timotej.advent.y2016;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Advent16b {

    public static void main(String[] args) throws IOException, ExecutionException {
        String svar = new Advent16b().calc(Online.get().get(0));
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private String calc(String str) throws ExecutionException {
        while (str.length() < 35651584) {
            StringBuilder next = new StringBuilder(str);
            next.append("0");
            for (int i = str.length() - 1; i >= 0; i--) {
                char c = str.charAt(i);
                next.append(c == '1' ? '0' : '1');
            }
            str = next.toString();
        }
        str = str.substring(0, 35651584);
        do {
            StringBuilder next = new StringBuilder();
            for (int i = 0; i < str.length(); i += 2) {
                char c1 = str.charAt(i);
                char c2 = str.charAt(i + 1);
                next.append(c1 == c2 ? '1' : '0');
            }
            str = next.toString();
        } while (str.length() % 2 == 0);
        return str;
    }
}