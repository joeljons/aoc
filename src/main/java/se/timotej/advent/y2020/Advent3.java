package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.List;

public class Advent3 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent3().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int sum = 0;
        int x = 0;
        int maxy = strs.size();
        int maxx = strs.get(0).length();
        for (int y = 0; y < maxy; y++) {
            if (strs.get(y).charAt(x) == '#') {
                sum++;
            }
            x = (x + 3) % maxx;
        }
        return sum;

    }

}
