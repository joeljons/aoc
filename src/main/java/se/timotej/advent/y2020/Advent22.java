package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Advent22 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent22().calc(Online.get());
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
        while (!players.get(0).isEmpty() && !players.get(1).isEmpty()) {
            final Integer p0 = players.get(0).remove(0);
            final Integer p1 = players.get(1).remove(0);
            if (p0 > p1) {
                players.get(0).add(p0);
                players.get(0).add(p1);
            } else {
                players.get(1).add(p1);
                players.get(1).add(p0);
            }
        }
        for (List<Integer> player : players) {
            if (!player.isEmpty()) {
                long mult = 1;
                for (int i = player.size() - 1; i >= 0; i--) {
                    sum += player.get(i) * mult;
                    mult++;
                }
            }
        }

        return sum;
    }
}