package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Advent7b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent7b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    Map<String, List<Bag>> g = new HashMap<>();

    private int calc(List<String> strs) {
        Pattern pattern = Pattern.compile("(.*) bag(s?)");
        Pattern pattern2 = Pattern.compile("(\\d+) (.*) bag(s?)(\\.?)");
        for (String str : strs) {
            String[] split = str.split(" contain ");
            Matcher matcher = pattern.matcher(split[0]);
            matcher.matches();
            String from = matcher.group(1);
            split = split[1].split(", ");
            List<Bag> bags = new ArrayList<>();
            for (String s : split) {
                matcher = pattern2.matcher(s);
                if (matcher.matches()) {
                    bags.add(new Bag(matcher.group(2), Integer.parseInt(matcher.group(1))));
                }
            }
            g.put(from, bags);
        }

        return getContains("shiny gold") - 1;
    }

    Map<String, Integer> cache = new HashMap<>();

    private int getContains(String color) {
        if (cache.containsKey(color)) {
            return cache.get(color);
        }
        int sum = 1;
        for (Bag bag : g.get(color)) {
            sum += bag.count * getContains(bag.color);
        }
        cache.put(color, sum);
        return sum;
    }

    private static class Bag {
        String color;
        int count;

        public Bag(String color, int count) {
            this.color = color;
            this.count = count;
        }
    }
}
