package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.List;

public class Advent3b {

    public static void main(String[] args) throws IOException {
        int a = new Advent3b().calc(Online.get(), 1, 1);
        int b = new Advent3b().calc(Online.get(), 3, 1);
        int c = new Advent3b().calc(Online.get(), 5, 1);
        int d = new Advent3b().calc(Online.get(), 7, 1);
        int e = new Advent3b().calc(Online.get(), 1, 2);
        int svar = a * b * c * d * e;
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs, int dx, int dy) {
        int sum = 0;
        int x = 0;
        int maxy = strs.size();
        int maxx = strs.get(0).length();
        for (int y = 0; y < maxy; y += dy) {
            if (strs.get(y).charAt(x) == '#') {
                sum++;
            }
            x = (x + dx) % maxx;
        }
        return sum;

    }

}
