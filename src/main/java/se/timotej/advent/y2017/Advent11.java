package se.timotej.advent.y2017;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class Advent11 {
    public static void main(String[] args) throws IOException {
        new Advent11().calc(Collections.singletonList("ne,ne,ne"));
        new Advent11().calc(Collections.singletonList("ne,ne,sw,sw"));
        new Advent11().calc(Collections.singletonList("ne,ne,s,s"));
        new Advent11().calc(Collections.singletonList("se,sw,se,sw,sw"));
        new Advent11().calc(Online.get(11));
    }

    int x = 0;
    int y = 0;

    private void calc(List<String> strs) {
        String str = strs.get(0);
        int m = 0;
        for (String s : str.split(",")) {
            if (s.equals("n")) {
                y++;
            } else if (s.equals("s")) {
                y--;
            } else if (s.equals("ne")) {
                x++;
            } else if (s.equals("se")) {
                x++;
                y--;
            } else if (s.equals("nw")) {
                x--;
                y++;
            } else if (s.equals("sw")) {
                x--;
            } else {
                throw new RuntimeException("Vafan: " + s);
            }
            m = max(m, hexDistance(x, y));
        }
        int svar = abs(x) + abs(y);
        System.out.println("hexDistance(x,y) = " + hexDistance(x, y));
        System.out.println("max = " + m);
        System.out.println();

    }

    int hexDistance(int x, int y) {
        if (x > 0 && y > 0 || x < 0 && y < 0) {
            return abs(x) + abs(y);
        } else {
            return max(abs(x), abs(y));
        }
    }
}