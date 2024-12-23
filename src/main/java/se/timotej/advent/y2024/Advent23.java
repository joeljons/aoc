package se.timotej.advent.y2024;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Advent23 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent23().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;

        Multimap<String, String> map = HashMultimap.create();

        for (String str : strs) {
            String[] split = str.split("-");
            map.put(split[0], split[1]);
            map.put(split[1], split[0]);
        }
        ArrayList<String> g = new ArrayList<>(map.keySet());
        for (int i = 0; i < g.size(); i++) {
            for (int j = i + 1; j < g.size(); j++) {
                if (map.get(g.get(i)).contains(g.get(j))) {
                    for (int k = j + 1; k < g.size(); k++) {
                        if ((g.get(i).startsWith("t") || g.get(j).startsWith("t") || g.get(k).startsWith("t"))
                                && map.get(g.get(j)).contains(g.get(k))
                                && map.get(g.get(k)).contains(g.get(i))) {
                            svar++;
                        }
                    }
                }
            }
        }

        return svar;
    }

}
