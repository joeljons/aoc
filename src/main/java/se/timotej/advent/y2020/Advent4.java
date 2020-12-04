package se.timotej.advent.y2020;

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
        int sum = 0;
        Set<String> needed = new HashSet<>(Set.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"));
        for (String str : strs) {
            if (str.isEmpty()) {
                if (needed.isEmpty()) {
                    sum++;
                }
                needed = new HashSet<>(Set.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"));
            }
            String[] keyvalue = str.split(" ");
            for (String s : keyvalue) {
                String[] split = s.split(":");
                needed.remove(split[0]);
            }

        }
        if (needed.isEmpty()) {
            sum++;
        }
        return sum;

    }

}
