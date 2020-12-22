package se.timotej.advent.y2020;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Advent22b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent22b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long sum = 0;
        List<List<Integer>> players = new ArrayList<>();
        for (List<String> strings : Util.splitByDoubleNewline(strs)) {
            List<Integer> player = new LinkedList<>();
            for (String str : strings.subList(1, strings.size())) {
                player.add(Integer.parseInt(str));
            }
            players.add(player);
        }
        List<Integer> player = recurse(players.get(0), players.get(1))
                ? players.get(0)
                : players.get(1);
        if (!player.isEmpty()) {
            long mult = 1;
            for (int i = player.size() - 1; i >= 0; i--) {
                sum += player.get(i) * mult;
                mult++;
            }
        }

        return sum;
    }

    private boolean recurse(List<Integer> player0, List<Integer> player1) {
        Set<Pair<List<Integer>, List<Integer>>> seenBefore = new HashSet<>();
        while (!player0.isEmpty() && !player1.isEmpty()) {
            if(!seenBefore.add(Pair.of(new ArrayList<>(player0), new ArrayList<>(player1)))){
                return true;
            }
            final Integer p0 = player0.remove(0);
            final Integer p1 = player1.remove(0);
            boolean p0Win;
            if (player0.size() >= p0 && player1.size() >= p1) {
                p0Win = recurse(new LinkedList<>(player0.subList(0, p0)),
                        new LinkedList<>(player1.subList(0, p1)));
            } else {
                p0Win = p0 > p1;
            }
            if (p0Win) {
                player0.add(p0);
                player0.add(p1);
            } else {
                player1.add(p1);
                player1.add(p0);
            }
        }
        return !player0.isEmpty();
    }
}