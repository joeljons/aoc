package se.timotej.advent.y2017;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Advent12 {
    public static void main(String[] args) throws IOException {
        new Advent12().calc(Online.get(12));
    }

    private void calc(List<String> strs) {
        Map<Integer, List<Integer>> g = new HashMap<>();
        for (String str : strs) {
            String[] split = str.split(" <-> ");
            int from = Integer.parseInt(split[0]);
            List<Integer> list = Util.intList(Util.intArrayComma(split[1]));
            g.put(from, list);
        }
        boolean changed = true;
        Set<Integer> reachable = new HashSet<>();
        reachable.add(0);
        for(int i=0;changed;i++){
            changed = false;
            for (Integer from : new ArrayList<Integer>(reachable)) {
                for (Integer to : g.get(from)) {
                    if(!reachable.contains(to)){
                        reachable.add(to);
                        changed = true;
                    }
                }
            }
        }
        System.out.println("reachable.size() = " + reachable.size());

    }

}