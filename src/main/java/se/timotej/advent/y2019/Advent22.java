package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Advent22 {

    public static void main(String[] args) throws IOException {
        long start = System.nanoTime();
        long svar = new Advent22().calc(Online.get());
        long duration = (System.nanoTime() - start) / 1000000;
        System.out.println("duration = " + duration);
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        List<Integer> cards = new LinkedList<>();
        for (int i = 0; i < 10007; i++) {
            cards.add(i);
        }
        for (String str : strs) {
            if (str.equals("deal into new stack")) {
                Collections.reverse(cards);
            } else if (str.startsWith("cut")) {
                int cut = Util.findAllInts(str).get(0);
                if (cut < 0) {
                    cut = cards.size() + cut;
                }
                List<Integer> tmp = new LinkedList<>();
                for (int i = 0; i < cut; i++) {
                    tmp.add(cards.remove(0));
                }
                cards.addAll(tmp);
            } else if (str.startsWith("deal with increment ")) {
                int increment = Util.findAllInts(str).get(0);
                List<Integer> tmp = new ArrayList<>();
                for (int i = 0; i < cards.size(); i++) {
                    tmp.add(-1);
                }
                int pos = 0;
                for (int i = 0; i < cards.size(); i++) {
                    tmp.set(pos, cards.get(i));
                    pos = (pos + increment) % cards.size();
                }
                cards.clear();
                cards.addAll(tmp);
            } else {
                throw new RuntimeException(str);
            }
        }
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i) == 2019) {
                return i;
            }
        }
        return 0;
    }
}
