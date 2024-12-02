package se.timotej.advent.y2024;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Advent1 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent1().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int svar = 0;
        List<Integer> first = new ArrayList<>();
        List<Integer> second = new ArrayList<>();
        for (String str : strs) {
            int[] line = Util.intArray(str);
            first.add(line[0]);
            second.add(line[1]);
        }
        Collections.sort(first);
        Collections.sort(second);
        for (int i = 0; i < first.size(); i++) {
             svar += Math.abs(first.get(i) - second.get(i));
        }
        return svar;
    }
}
