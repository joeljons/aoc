package se.timotej.advent.y2024;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Advent23b {

    public static void main(String[] args) throws IOException {
        String svar = new Advent23b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    String svar = "";

    private String calc(List<String> strs) {
        Multimap<String, String> map = HashMultimap.create();

        for (String str : strs) {
            String[] split = str.split("-");
            map.put(split[0], split[1]);
            map.put(split[1], split[0]);
        }
        ArrayList<String> g = new ArrayList<>(map.keySet());
        Collections.sort(g);
        rek(g, map, new ArrayList<>(), 0);

        return svar;
    }

    private void rek(ArrayList<String> g, Multimap<String, String> map, List<String> inAlready, int startIndex) {
        if (startIndex == g.size()) {
            if (inAlready.size() > (svar.length() + 1) / 3) {
                svar = StringUtils.join(inAlready, ",");
            }
            return;
        }
        rek(g, map, inAlready, startIndex + 1);
        boolean canAdd = true;
        Collection<String> myConnections = map.get(g.get(startIndex));
        for (String friend : inAlready) {
            if (!myConnections.contains(friend)) {
                canAdd = false;
                break;
            }
        }
        if (canAdd) {
            inAlready.add(g.get(startIndex));
            rek(g, map, inAlready, startIndex + 1);
            inAlready.removeLast();
        }
    }

}
