package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Advent7 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent7().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        Pattern pattern = Pattern.compile("(.*) bag(s?)");
        Pattern pattern2 = Pattern.compile("(\\d+) (.*) bag(s?)(\\.?)");
        Map<String, List<Bag>> g = new HashMap<>();
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

        Set<String> have = new HashSet<>();
        have.add("shiny gold");
        boolean added;
        do {
            added = false;
            for (Map.Entry<String, List<Bag>> entry : g.entrySet()) {
                for (Bag bag : entry.getValue()) {
                    if (have.contains(bag.color) && !have.contains(entry.getKey())) {
                        added = true;
                        have.add(entry.getKey());
                    }
                }
            }
        } while (added);
        return have.size() - 1;
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
