package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent5b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent5b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {

        Set<Integer> found = new HashSet<>();
        for (String str : strs) {
            int id = getId(str);
            found.add(id);
        }
        for (int i = 0; ; i++) {
            if (!found.contains(i) && found.contains(i - 1) && found.contains(i + 1)) {
                return i;
            }
        }
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
