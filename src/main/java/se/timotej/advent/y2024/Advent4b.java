package se.timotej.advent.y2024;

import java.io.IOException;
import java.util.List;

public class Advent4b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent4b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int svar = 0;
        int maxy = strs.size();
        int maxx = strs.get(0).length();
        for (int y = 1; y < maxy - 1; y++) {
            for (int x = 1; x < maxx - 1; x++) {
                if (strs.get(y).charAt(x) == 'A') {
                    if (strs.get(y - 1).charAt(x - 1) == 'M' && strs.get(y + 1).charAt(x + 1) == 'S'
                            || strs.get(y - 1).charAt(x - 1) == 'S' && strs.get(y + 1).charAt(x + 1) == 'M') {
                        if (strs.get(y + 1).charAt(x - 1) == 'M' && strs.get(y - 1).charAt(x + 1) == 'S'
                                || strs.get(y + 1).charAt(x - 1) == 'S' && strs.get(y - 1).charAt(x + 1) == 'M') {
                            svar++;
                        }
                    }
                }
            }
        }
        return svar;
    }
}