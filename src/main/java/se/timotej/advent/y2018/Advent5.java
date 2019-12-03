package se.timotej.advent.y2018;

import java.io.IOException;

public class Advent5 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent5().calc(Online.get().get(0));

        System.out.println("svar = " + svar);
//        Online.submit(svar);
    }

    private int calc(String str) {
        int lastSize;
        do {
            lastSize = str.length();
            for (char c = 'a'; c <= 'z'; c++) {
                char C = (char)('A'-'a'+c);
                String needle = new String(new char[]{c, C});
                String needle2 = new String(new char[]{C, c});
                str = str.replace(needle, "");
                str = str.replace(needle2, "");
            }
        } while (lastSize != str.length());
        return str.length();
    }

}
