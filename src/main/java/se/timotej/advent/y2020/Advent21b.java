package se.timotej.advent.y2020;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Advent21b {

    public static void main(String[] args) throws IOException {
        String svar = new Advent21b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private String calc(List<String> strs) {
        Set<String> allAllrg = new HashSet<>();
        Set<String> allIngr = new HashSet<>();
        List<Pair<Set<String>, Set<String>>> foods = new ArrayList<>();
        for (String str : strs) {
            String[] split = str.split(" \\(contains ");
            String[] ingredients = split[0].split(" ");
            String[] contains = split[1].replace(")", "").split(", ");
            Set<String> myIngr = new HashSet<>();
            for (String ingredient : ingredients) {
                allIngr.add(ingredient);
                myIngr.add(ingredient);
            }
            Set<String> myAllrg = new HashSet<>();
            for (String contain : contains) {
                allAllrg.add(contain);
                myAllrg.add(contain);
            }
            foods.add(Pair.of(myIngr, myAllrg));
        }
        Set<String> bad = new HashSet<>();
        for (String allrgn : allAllrg) {
            Set<String> maybe = new HashSet<>(allIngr);
            for (Pair<Set<String>, Set<String>> food : foods) {
                final Set<String> ingr = food.getLeft();
                final Set<String> allrg = food.getRight();
                if (allrg.contains(allrgn)) {
                    maybe = maybe.stream().filter(ingr::contains).collect(Collectors.toSet());
                }
            }
            Set<String> meh = maybe;
            allIngr.stream().filter(meh::contains).forEach(bad::add);
        }
        String[] bads = new ArrayList<>(bad).toArray(new String[0]);
        Arrays.sort(bads);
        String[] alrgs = new ArrayList<>(allAllrg).toArray(new String[0]);
        Arrays.sort(alrgs);
        do {
            Map<String, String> g = new HashMap<>();
            for (int i = 0; i < bads.length; i++) {
                g.put(alrgs[i], bads[i]);
            }
            boolean ok = true;
            for (Pair<Set<String>, Set<String>> food : foods) {
                final Set<String> ingr = food.getLeft();
                final Set<String> allrg = food.getRight();
                for (String al : allrg) {
                    if (!ingr.contains(g.get(al))) {
                        ok = false;
                        break;
                    }
                }
            }
            if (ok) {
                return String.join(",", bads);
            }
        } while (Util.nextPermutation(bads));

        return "";
    }
}