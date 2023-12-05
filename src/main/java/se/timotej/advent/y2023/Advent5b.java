package se.timotej.advent.y2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Advent5b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent5b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = Long.MAX_VALUE;
        List<List<String>> input = Util.splitByDoubleNewline(strs);

        List<Long> allSeeds = Util.findAllLongs(input.get(0).get(0));


        List<List<List<Long>>> allMaps = new ArrayList<>();
        for (List<String> mapping : input.subList(1, input.size())) {
            List<List<Long>> map = new ArrayList<>();
            for (String line : mapping.subList(1, mapping.size())) {
                List<Long> m = Util.findAllLongs(line);
                map.add(m);
            }
            allMaps.add(map);
        }

        for (int i = 0; i < allSeeds.size() / 2; i++) {
            for (int j = 0; j < allSeeds.get(2 * i + 1); j++) {
                long now = allSeeds.get(2 * i) + j;

                for (List<List<Long>> mapping : allMaps) {
                    for (List<Long> m : mapping) {
                        if (now >= m.get(1) && now < m.get(1) + m.get(2)) {
                            now = now - m.get(1) + m.get(0);
                            break;
                        }
                    }
                }
                if (now < svar) {
                    svar = now;
                }
            }
        }
        return svar;
    }
}
