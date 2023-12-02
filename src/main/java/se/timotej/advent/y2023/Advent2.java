package se.timotej.advent.y2023;

import java.io.IOException;
import java.util.List;

public class Advent2 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent2().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int svar = 0;
        for (String game : strs) {
            boolean possible = true;

            String[] split1 = game.split(":");
            String[] split2 = split1[1].split(";");
            for (String set : split2) {
                String[] split3 = set.split(",");
                for (String qube : split3) {
                    int limit = 0;
                    if (qube.contains("red")) {
                        limit = 12;
                    }
                    if (qube.contains("green")) {
                        limit = 13;
                    }
                    if (qube.contains("blue")) {
                        limit = 14;
                    }
                    Integer qubeCount = Util.findAllInts(qube).get(0);
                    if (qubeCount > limit) {
                        possible = false;
                    }
                }
            }
            if (possible) {
                svar += Util.findAllInts(split1[0]).get(0);
            }
        }
        return svar;
    }
}
