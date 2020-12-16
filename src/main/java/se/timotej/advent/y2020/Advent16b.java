package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Advent16b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent16b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        List<List<String>> lists = Util.splitByDoubleNewline(strs);

        List<Integer> myTicket = Util.findAllPositiveInts(lists.get(1).get(1));

        Map<String, Set<Integer>> valids = new HashMap<>();
        for (String str : lists.get(0)) {
            Set<Integer> valid = new HashSet<>();
            List<Integer> allInts = Util.findAllPositiveInts(str);
            for (int i = allInts.get(0); i <= allInts.get(1); i++) {
                valid.add(i);
            }
            for (int i = allInts.get(2); i <= allInts.get(3); i++) {
                valid.add(i);
            }
            valids.put(str.substring(0, str.indexOf(":")), valid);
        }

        List<List<Integer>> validTickets = new ArrayList<>();
        validTickets.add(myTicket);
        for (String str : lists.get(2)) {
            int minAntal = Integer.MAX_VALUE;
            List<Integer> allInts = Util.findAllPositiveInts(str);
            if (allInts.isEmpty()) {
                continue;
            }
            for (Integer allInt : allInts) {
                int antal = 0;
                for (Set<Integer> valid : valids.values()) {
                    if (valid.contains(allInt)) {
                        antal++;
                    }
                }
                minAntal = Math.min(minAntal, antal);
            }
            if (minAntal != 0) {
                validTickets.add(allInts);
            }
        }
        Map<String, Set<Integer>> mapping = new HashMap<>();
        for (String field : valids.keySet()) {
            Set<Integer> apa = new HashSet<>();
            for (int j = 0; j < 20; j++) {
                apa.add(j);
            }
            mapping.put(field, apa);
        }
        for (List<Integer> validTicket : validTickets) {
            for (int pos = 0; pos < 20; pos++) {
                Integer integer = validTicket.get(pos);
                for (Map.Entry<String, Set<Integer>> stringSetEntry : valids.entrySet()) {
                    if (!stringSetEntry.getValue().contains(integer)) {
                        mapping.get(stringSetEntry.getKey()).remove(pos);
                    }
                }
            }
        }
        for (int i = 0; i < 20; i++) {
            Set<Integer> onlyPresentOnce = new HashSet<>();
            for (Set<Integer> value : mapping.values()) {
                if (value.size() == 1) {
                    onlyPresentOnce.add(value.iterator().next());
                }
            }
            for (Set<Integer> value : mapping.values()) {
                if (value.size() != 1) {
                    value.removeAll(onlyPresentOnce);
                }
            }
        }
        long prod = 1;
        for (Map.Entry<String, Set<Integer>> stringSetEntry : mapping.entrySet()) {
            if (stringSetEntry.getKey().startsWith("departure")) {
                prod *= myTicket.get(stringSetEntry.getValue().iterator().next());
            }
        }
        System.out.println("mapping = " + mapping);
        return prod;
    }
}
