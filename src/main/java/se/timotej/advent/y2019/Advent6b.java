package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent6b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent6b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        Map<String, String> orbits = new HashMap<>();
        for (String str : strs) {
            String[] split = str.split("\\)");
            orbits.put(split[1], split[0]);
        }
        String s = "YOU";
        int i = 0;
        Map<String, Integer> dists = new HashMap<>();
        while (s != null) {
            s = orbits.get(s);
            dists.put(s, i++);
        }
        s = "SAN";
        int svar = 0;
        while (!dists.containsKey(s)) {
            s = orbits.get(s);
            svar++;
        }
        return dists.get(s) + svar - 1;
    }


}
