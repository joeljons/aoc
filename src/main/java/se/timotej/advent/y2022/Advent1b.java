package se.timotej.advent.y2022;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Advent1b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent1b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int sum = 0;
        List<Integer> apa = new ArrayList<>();
        for (String str : strs) {
            if (str.isBlank()) {
                apa.add(-sum);
                sum = 0;
            } else {
                sum += Integer.parseInt(str);
            }
        }
        Collections.sort(apa);
        return -(apa.get(0) + apa.get(1) + apa.get(2));
    }

}
