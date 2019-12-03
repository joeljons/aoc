package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.LongStream;

public class Advent9b {

    private int[] g;

    public static void main(String[] args) throws IOException {
        long svar = new Advent9b().calc(Online.get());
        System.out.println("svar = " + svar);
//        Online.submit(9, 2, svar);
    }

    int players = 493;
    int lastMarble = 7186300;
//    int lastMarble = 71863;
//    int players = 9;
//    int lastMarble = 25;

    private long calc(List<String> strs) {
        int nuPlayer = 0;
        long[] scores = new long[players + 1];
        LinkedList<Integer> g = new LinkedList<>();
        g.add(0);
        ListIterator<Integer> it = g.listIterator();
        for (int nuMarble = 1; nuMarble <= lastMarble; nuMarble++) {
            nuPlayer = (nuPlayer % players) + 1;
            if (nuMarble % 23 == 0) {
                scores[nuPlayer] += nuMarble;
                for (int i = 0; i < 7; i++) {
                    if (it.hasPrevious()) {
                        it.previous();
                    } else {
                        it = g.listIterator(g.size() - 1);
                    }
                }
                if (!it.hasPrevious()) {
                    it = g.listIterator(g.size());
                }
                scores[nuPlayer] += it.previous();
                it.remove();
                it.next();
            } else if (g.size() == 1) {
                it.next();
                it.add(nuMarble);
                it.previous();
            } else if (g.size() == 2) {
                it.add(nuMarble);
            } else {
                if (!it.hasNext()) {
                    it = g.listIterator();
                }
                it.next();
                it.add(nuMarble);
            }
        }

        return LongStream.of(scores).max().getAsLong();
    }

}
