package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent16 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent16().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long sum = 0;
        List<List<String>> lists = Util.splitByDoubleNewline(strs);

        Set<Integer> valid = new HashSet<>();
        for (String str : lists.get(0)) {
            List<Integer> allInts = Util.findAllPositiveInts(str);
            for (int i = allInts.get(0); i <= allInts.get(1); i++) {
                valid.add(i);
            }
            for (int i = allInts.get(2); i <= allInts.get(3); i++) {
                valid.add(i);
            }
        }

        for (String str : lists.get(2)) {
            for (Integer allInt : Util.findAllPositiveInts(str)) {
                if (!valid.contains(allInt)) {
                    sum += allInt;
                }
            }
        }
        return sum;
    }
}
