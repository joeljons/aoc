package se.timotej.advent.y2017;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Advent12b {
    public static void main(String[] args) throws IOException {
        new Advent12b().calc(Online.get(12));
    }

    private void calc(List<String> strs) {
        Map<Integer, List<Integer>> g = new HashMap<>();
        for (String str : strs) {
            String[] split = str.split(" <-> ");
            int from = Integer.parseInt(split[0]);
            List<Integer> list = new ArrayList<>();
            for (int i : Util.intArrayComma(split[1])) {
                list.add(i);
            }
            g.put(from, list);
        }
        int groupCount=0;
        while(!g.isEmpty()) {
            groupCount++;
            boolean changed = true;
            Set<Integer> reachable = new HashSet<>();
            reachable.add(g.keySet().iterator().next());
            for (int i = 0; changed; i++) {
                changed = false;
                for (Integer from : new ArrayList<Integer>(reachable)) {
                    for (Integer to : g.get(from)) {
                        if (!reachable.contains(to)) {
                            reachable.add(to);
                            changed = true;
                        }
                    }
                }
            }
            for (Integer integer : reachable) {
                g.remove(integer);
            }
            System.out.println("reachable.size() = " + reachable.size());
        }
        System.out.println("groupCount = " + groupCount);
    }
}