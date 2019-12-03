package se.timotej.advent.y2016;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Advent22 {

    public static void main(String[] args) throws IOException, ExecutionException {
        int svar = new Advent22().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(String.valueOf(svar));
    }

    private int calc(List<String> strs) {
        List<Pair<Integer, Integer>> g = new ArrayList<>();
        for (String str : strs.subList(2, strs.size())) {
            String[] line = str.split("[xy\\- T]+");
            int use = Integer.parseInt(line[4]);
            int avail = Integer.parseInt(line[5]);
            g.add(Pair.of(use, avail));
        }
        int svar = 0;
        for (int i = 0; i < g.size(); i++) {
            Pair<Integer, Integer> A = g.get(i);
            if (A.getLeft() == 0) {
                continue;
            }
            for (int j = 0; j < g.size(); j++) {
                if (i == j) {
                    continue;
                }
                Pair<Integer, Integer> B = g.get(j);
                if (A.getLeft() <= B.getRight()) {
                    svar++;
                }
            }
        }
        return svar;
    }

}