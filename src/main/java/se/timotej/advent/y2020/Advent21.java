package se.timotej.advent.y2020;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Advent21 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent21().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long sum = 0;
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
        for (Pair<Set<String>, Set<String>> food : foods) {
            final Set<String> ingr = food.getLeft();
            for (String s : ingr) {
                if (!bad.contains(s)) {
                    sum++;
                }
            }
        }
        return sum;
    }
}