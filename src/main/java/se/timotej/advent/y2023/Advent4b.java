package se.timotej.advent.y2023;

import java.io.IOException;
import java.util.*;

public class Advent4b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent4b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int svar = 0;
        int[] cardCopies = new int[strs.size() + 1];
        Arrays.fill(cardCopies, 1);
        for (String str : strs) {
            String[] split = str.split("\\|");
            List<Integer> allInts = Util.findAllInts(split[0]);
            Integer cardNum = allInts.get(0);
            Set<Integer> winning = new HashSet<>(allInts.subList(1, allInts.size()));
            int point = 0;
            for (Integer num : Util.findAllInts(split[1])) {
                if (winning.contains(num)) {
                    point++;

                }
            }
            svar += cardCopies[cardNum];
            for (int i = cardNum + 1; i <= cardNum + point; i++) {
                cardCopies[i] += cardCopies[cardNum];
            }
        }
        return svar;
    }
}
