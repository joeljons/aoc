package se.timotej.advent.y2023;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Advent15b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent15b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;
        String[] split = strs.get(0).split(",");
        List<List<Pair<String, Integer>>> boxes = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            boxes.add(new ArrayList<>());
        }
        for (String s : split) {
            if (s.contains("=")) {
                String[] split2 = s.split("=");
                String label = split2[0];
                int hash = hash(label);
                List<Pair<String, Integer>> box = boxes.get(hash);
                boolean replaced = false;
                for (int i = 0; i < box.size(); i++) {
                    if (box.get(i).getLeft().equals(label)) {
                        box.set(i, Pair.of(label, Integer.parseInt(split2[1])));
                        replaced = true;
                        break;
                    }
                }
                if (!replaced) {
                    box.add(Pair.of(label, Integer.parseInt(split2[1])));
                }
            } else {
                String label = s.split("-")[0];
                int hash = hash(label);
                List<Pair<String, Integer>> box = boxes.get(hash);
                for (int i = 0; i < box.size(); i++) {
                    if (box.get(i).getLeft().equals(label)) {
                        box.remove(i);
                        break;
                    }
                }
            }
        }
        for (int i = 0; i < 256; i++) {
            List<Pair<String, Integer>> box = boxes.get(i);
            for (int j = 0; j < box.size(); j++) {
                svar += (i + 1) * (j + 1) * box.get(j).getRight();
            }
        }
        return svar;
    }

    private int hash(String s) {
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            val += s.charAt(i);
            val *= 17;
            val %= 256;
        }
        return val;
    }
}
