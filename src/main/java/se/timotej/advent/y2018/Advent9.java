package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Advent9 {

    private int[] g;

    public static void main(String[] args) throws IOException {
        int svar = new Advent9().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

        int players = 493;
    int lastMarble = 71863;
//    int players = 9;
//    int lastMarble = 25;

    private int calc(List<String> strs) {
        int nuPlayer = 0;
        int[] scores = new int[players + 1];
        List<Integer> g = new ArrayList<>();
        g.add(0);
        int pos = 0;
        for (int nuMarble = 1; nuMarble <= lastMarble; nuMarble++) {
            nuPlayer = (nuPlayer % players) + 1;
            if (nuMarble % 23 == 0) {
                scores[nuPlayer] += nuMarble;
                pos = (g.size()+pos-7)%g.size();
                scores[nuPlayer] += g.remove(pos);
            } else if (g.size() == 1) {
                g.add((pos + 1) % (g.size() + 1), nuMarble);
                pos = (pos + 1) % g.size();
            } else if (g.size() == 2) {
                g.add(pos, nuMarble);
                pos = pos;
            } else if (pos == g.size() - 1) {
                g.add(1, nuMarble);
                pos = 1;
            } else {
                g.add((pos + 2) % (g.size() + 1), nuMarble);
                pos = (pos + 2) % g.size();
            }
        }

        return IntStream.of(scores).max().getAsInt();
    }

}
