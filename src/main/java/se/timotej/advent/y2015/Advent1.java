package se.timotej.advent.y2015;

import java.io.IOException;
import java.util.List;

public class Advent1 {
    public static void main(String[] args) throws IOException {
        new Advent1().calc(Online.get(1));
    }

    private void calc(List<String> strs) {
        String str = strs.get(0);
        int hojd = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            hojd += c == '(' ? 1: -1;
            if (hojd < 0) {
                System.out.println("i = " + (i + 1));
            }
        }
        System.out.println("hojd = " + hojd);
    }

}
