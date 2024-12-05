package se.timotej.advent.y2024;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.io.IOException;
import java.util.List;

public class Advent5 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent5().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int svar = 0;
        List<List<String>> input = Util.splitByDoubleNewline(strs);
        Multimap<Integer, Integer> rules = ArrayListMultimap.create();
        for (String line : input.get(0)) {
            List<Integer> rule = Util.findAllInts(line);
            rules.put(rule.get(0), rule.get(1));
        }
        for (String line : input.get(1)) {
            List<Integer> pages = Util.findAllInts(line);
            boolean ok = true;
            for (int i = 0; i < pages.size(); i++) {
                int page = pages.get(i);
                if (rules.containsKey(page)) {
                    for (Integer otherPage : rules.get(page)) {
                        int otherIndex = pages.indexOf(otherPage);
                        if (otherIndex != -1 && otherIndex < i) {
                            ok = false;
                            break;
                        }
                    }
                }
            }
            if (ok) {
                svar += pages.get(pages.size() / 2);
            }
        }

        return svar;
    }
}
