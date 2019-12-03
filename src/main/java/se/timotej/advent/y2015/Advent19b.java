package se.timotej.advent.y2015;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent19b {
    public static void main(String[] args) throws IOException {
        int svar = new Advent19b().calc(Online.get(19));
        System.out.println("svar = " + svar);
        //Online.submit(19, 2, svar);
    }

    int calc(List<String> strs) {
        String goal = strs.get(strs.size() - 1);
        Set<String> g = new HashSet<>();
        g.add(goal);
        int count = 0;
        List<Pair<String, String>> replacements = new ArrayList<>();
        for (int i = 0; i < strs.size() - 2; i++) {
            String[] line = strs.get(i).split(" => ");
            replacements.add(Pair.of(line[0], line[1]));
        }
        do {
            count++;
            System.out.println("count = " + count);
            System.out.println("g.size() = " + g.size());
            Set<String> g2 = new HashSet<>();
            Set<String> g3 = new HashSet<>();
            int startindex = 500;
            do {
                //System.out.println("startindex = " + startindex);
                for (Pair<String, String> replacement : replacements) {
                    String from = replacement.getRight();
                    String to = replacement.getLeft();
                    for (String start : g) {
                        if (start.startsWith(from, startindex)) {
                            g2.add(start.substring(0, startindex) + to + start.substring(startindex + from.length()));
                            System.out.println("replacement = " + replacement);
                        }
                    }
                }
                startindex--;
                if(startindex<0){
                    break;
                }
            } while (g2.size() == 0);
            startindex = 0;
            do {
                //System.out.println("startindex = " + startindex);
                for (Pair<String, String> replacement : replacements) {
                    String from = replacement.getRight();
                    String to = replacement.getLeft();
                    for (String start : g) {
                        if (start.startsWith(from, startindex)) {
                            g3.add(start.substring(0, startindex) + to + start.substring(startindex + from.length()));
                            System.out.println("replacement = " + replacement);
                        }
                    }
                }
                startindex++;
                if(startindex>500){
                    break;
                }
            } while (g2.size() == 0);
            g2.addAll(g3);
            for (String s : g2) {
                System.out.println("s = " + s);
            }
            g = g2;
            if (count == 1000) {
                System.exit(0);
            }
        } while (!g.contains("e"));
        return count;
    }
}
