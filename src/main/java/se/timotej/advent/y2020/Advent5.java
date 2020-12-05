package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.List;

public class Advent5 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent5().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int hi = 0;
        for (String str : strs) {
            int id = getId(str);
            if (id > hi) {
                hi = id;
            }
        }
        return hi;
    }

    private int getId(String str) {
        int id = 0;
        int add = 64;
        for (int i = 0; i < 7; i++) {
            if (str.charAt(i) == 'B') {
                id += add;
            }
            add /= 2;
        }
        id *= 8;
        add = 4;
        for (int i = 0; i < 3; i++) {
            if (str.charAt(i + 7) == 'R') {
                id += add;
            }
            add /= 2;
        }
        return id;
    }

}
