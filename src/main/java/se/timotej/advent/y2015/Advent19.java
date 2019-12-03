package se.timotej.advent.y2015;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent19 {
    public static void main(String[] args) throws IOException {
        int svar = new Advent19().calc(Online.get(19));
        System.out.println("svar = " + svar);
        Online.submit(19, 1, svar);
    }

    int calc(List<String> strs) {
        String start = strs.get(strs.size() - 1);
        Set<String> g = new HashSet<>();
        for (int i = 0; i < strs.size() - 2; i++) {
            String[] line = strs.get(i).split(" => ");
            String from = line[0];
            String to = line[1];
            int j = 0;
            while (j != -1) {
                j = start.indexOf(from, j);
                if (j != -1) {
                    g.add(start.substring(0, j) + to + start.substring(j + from.length()));
                    j++;
                }
            }
        }
        return g.size();
    }
}
