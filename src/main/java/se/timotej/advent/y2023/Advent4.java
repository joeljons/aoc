package se.timotej.advent.y2023;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent4 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent4().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int svar = 0;
        for (String str : strs) {
            String[] split = str.split("\\|");
            List<Integer> allInts = Util.findAllInts(split[0]);
            Set<Integer> winning = new HashSet<>(allInts.subList(1, allInts.size()));
            int point = 1;
            for (Integer num : Util.findAllInts(split[1])) {
                if (winning.contains(num)) {
                    point *= 2;
                }
            }
            svar += point / 2;
        }
        return svar;
    }
}
