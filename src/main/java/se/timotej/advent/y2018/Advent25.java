package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Advent25 {

    public static void main(String[] args) throws IOException {
        String svar = new Advent25().calc(Online.get());
        System.out.println("svar = " + svar);
        //Online.submit(svar);
    }

    private String calc(List<String> strs) {
        int svar = 0;

        List<List<Integer>> g = new ArrayList<>();
        for (String str : strs) {
            List<Integer> ints = Util.findAllInts(str);
            g.add(ints);
        }
        while (!g.isEmpty()) {
            List<List<Integer>> nu = new ArrayList<>();
            List<Integer> a = g.remove(g.size() - 1);
            nu.add(a);
            do {
                List<List<Integer>> toAdd = new ArrayList<>();
                for (List<Integer> n : nu) {
                    for (List<Integer> andra : g) {
                        int dist = 0;
                        for (int i = 0; i < 4; i++) {
                            dist += Math.abs(n.get(i) - andra.get(i));
                        }
                        if (dist <= 3) {
                            toAdd.add(andra);
                        }
                    }
                }
                nu = toAdd;
                g.removeAll(toAdd);
            } while (!nu.isEmpty());
            svar++;
        }

        return String.valueOf(svar);
    }
}