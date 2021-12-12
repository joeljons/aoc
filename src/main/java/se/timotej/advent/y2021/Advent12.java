package se.timotej.advent.y2021;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent12 {

    public static void main(String[] args) throws IOException {
        var svar = new Advent12().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    Set<String> taken = new HashSet<>();
    int sum = 0;
    Multimap<String, String> g = ArrayListMultimap.create();

    private int calc(List<String> strs) {
        for (String str : strs) {
            String[] split = str.split("-");
            g.put(split[0], split[1]);
            g.put(split[1], split[0]);
        }
        rek("start");
        return sum;
    }

    private void rek(String pos) {
        if (pos.equals("end")) {
            sum++;
            return;
        }
        if (Character.isLowerCase(pos.charAt(0))) {
            if (taken.contains(pos)) {
                return;
            }
            taken.add(pos);
        }
        for (String next : g.get(pos)) {
            rek(next);
        }
        taken.remove(pos);
    }
}
