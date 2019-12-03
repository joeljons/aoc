package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Advent5b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent5b().calc(Online.get().get(0));

        System.out.println("svar = " + svar);
//        Online.submit(svar);
    }

    private int calc(String str) {
        int best = Integer.MAX_VALUE;
        for (char c = 'a'; c <= 'z'; c++) {
            System.out.println("c = " + c);
            char C = (char) ('A' - 'a' + c);
            int nu = calc2(str.replaceAll("" + C, "").replaceAll("" + c, ""));
            if (nu < best) {
                best = nu;
            }
        }
        return best;
    }

    private int calc2(String str) {
        List<Character> chars = new LinkedList<>();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            chars.add(c);
        }
        ListIterator<Character> it = chars.listIterator();
        while (it.hasNext()) {
            Character nu = it.next();
            if (it.hasNext()) {
                Character next = it.next();
                if (Math.abs(nu - next) == 32) {
                    it.remove();
                    it.previous();
                    it.remove();
                }
                if (it.hasPrevious()) {
                    it.previous();
                }
            }
        }
        return chars.size();
    }

    private int calc2_old(String str) {
        int lastSize;
        do {
            lastSize = str.length();
            for (char c = 'a'; c <= 'z'; c++) {
                char C = (char) ('A' - 'a' + c);
                String needle = new String(new char[]{c, C});
                String needle2 = new String(new char[]{C, c});
                str = str.replaceAll(needle, "");
                str = str.replaceAll(needle2, "");
            }
        } while (lastSize != str.length());
        return str.length();
    }

}
