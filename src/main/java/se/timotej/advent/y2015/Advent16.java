package se.timotej.advent.y2015;

import java.io.IOException;
import java.util.List;

public class Advent16 {
    public static void main(String[] args) throws IOException {
        int svar = new Advent16().calc(Online.get(16));
        System.out.println("svar = " + svar);
        Online.submit(16, 1, svar);
    }

    int calc(List<String> strs) {
        String[] lookfor = new String[]{
                "children: 3",
                "cats: 7",
                "samoyeds: 2",
                "pomeranians: 3",
                "akitas: 0",
                "vizslas: 0",
                "goldfish: 5",
                "trees: 3",
                "cars: 2",
                "perfumes: 1"
        };
        for (String str : strs) {
            int matches = 0;
            for (String lookf : lookfor) {
                if (str.contains(lookf)) {
                    matches++;
                }
            }
            if (matches == 3) {
                return Integer.parseInt(str.split("[ :]")[1]);
            }
        }
        return 0;
    }
}
