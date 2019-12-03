package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Advent23b_v8 {

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        String svar = new Advent23b_v8().calc(Online.get(23));
        long duration = System.currentTimeMillis() - start;
        System.out.println("duration = " + duration / 1000);
        System.out.println("svar = " + svar);
        // Online.submit(svar);
    }

    private static final int LIMIT = 10;

    private String calc(List<String> strs) {
        List<List<Integer>> g = new ArrayList<>();
        for (String str2 : strs) {
            List<Integer> ints = Util.findAllInts(str2);
            g.add(ints);
        }
        int bestDist = Integer.MAX_VALUE;
        int bestAntal = 0;
        for (List<Integer> from : g) {
            for (int i = 0; i < 6; i++) {
                int X = from.get(0);
                int Y = from.get(1);
                int Z = from.get(2);
                if (i == 0) {
                    X -= from.get(3);
                }
                if (i == 1) {
                    X += from.get(3);
                }
                if (i == 2) {
                    Y -= from.get(3);
                }
                if (i == 3) {
                    Y += from.get(3);
                }
                if (i == 4) {
                    Z -= from.get(3);
                }
                if (i == 5) {
                    Z += from.get(3);
                }
                for (int x = X - LIMIT; x <= X + LIMIT; x++) {
                    for (int y = Y - LIMIT; y <= Y + LIMIT; y++) {
                        for (int z = Z - LIMIT; z <= Z + LIMIT; z++) {
                            int antal = 0;
                            for (List<Integer> ints : g) {
                                int dist = abs(ints.get(0) - x)
                                        + abs(ints.get(1) - y)
                                        + abs(ints.get(2) - z);
                                if (dist <= ints.get(3)) {
                                    antal++;
                                }
                            }
                            int myDist = abs(x) + abs(y) + abs(z);
                            if (antal > bestAntal || (antal == bestAntal && myDist < bestDist)) {
                                bestDist = myDist;
                                bestAntal = antal;
                                System.out.println("bestDist = " + bestDist);
                                System.out.println("bestAntal = " + bestAntal);
                                System.out.println(x + "\t" + y + "\t" + z);
                            }
                        }
                    }
                }
            }
        }
        if (bestDist == 86012062) {
            System.out.println("Already tried this :-(");
            System.exit(0);
        } else if (bestDist < 86012062) {
            System.out.println("Too low :-(");
            System.exit(0);
        } else {
            System.out.println("Could it work?");
        }
        return String.valueOf(bestDist);
    }
}
