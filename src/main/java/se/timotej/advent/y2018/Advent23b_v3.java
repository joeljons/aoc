package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Advent23b_v3 {

    public static void main(String[] args) throws IOException {
        String svar = new Advent23b_v3().calc(Online.get(23));
        System.out.println("svar = " + svar);
//        Online.submit(svar);
    }

    private String calc(List<String> strs) {

        int best = 0;
        List<List<Integer>> g = new ArrayList<>();
        for (String str2 : strs) {
            g.add(Util.findAllInts(str2));
        }
        for (List<Integer> ints : g) {
            System.out.println(abs(ints.get(0)) + abs(ints.get(1)) + abs(ints.get(2)) - ints.get(3));
        }
        return "";
    }
}

//fel
//82265125