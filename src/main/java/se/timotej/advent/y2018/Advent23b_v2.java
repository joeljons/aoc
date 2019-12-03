package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.abs;

public class Advent23b_v2 {

    public static void main(String[] args) throws IOException {
        String svar = new Advent23b_v2().calc(Online.get(23));
        System.out.println("svar = " + svar);
//        Online.submit(svar);
    }

    private String calc(List<String> strs) {

        int best = 0;
        List<List<Integer>> g = new ArrayList<>();
        for (String str2 : strs) {
            g.add(Util.findAllInts(str2));
        }

      /*  List<Integer> daBest = null;
        for (List<Integer> strongest : g) {
            int svar = 0;
            for (List<Integer> ints : g) {
                int dist = abs(ints.get(0) - strongest.get(0))
                        + abs(ints.get(1) - strongest.get(1))
                        + abs(ints.get(2) - strongest.get(2));
                if (dist <= ints.get(3)) {
                    svar++;
                }
            }
            if (svar > best ) {
                best = svar;
                System.out.println(svar);
                daBest = strongest;
            }
        }*/

        List<Integer> daBest = Arrays.asList(
                12042665,
                29210764,
                45647863);
        daBest = new ArrayList<>(daBest);

        int startAntal = 0;
        for (List<Integer> ints : g) {
            int dist = abs(ints.get(0) - daBest.get(0))
                    + abs(ints.get(1) - daBest.get(1))
                    + abs(ints.get(2) - daBest.get(2));
            if (dist <= ints.get(3)) {
                startAntal++;
            }
        }
        int nuAntal;
        int closer = 0;
        int stall =0;
        do {
            closer++;
            Integer lastValue = daBest.get(closer % 3);
            daBest.set(closer%3, lastValue - Integer.signum(daBest.get(closer%3)));
            //daBest.set(1, daBest.get(1) - Integer.signum(daBest.get(1)));
            //daBest.set(2, daBest.get(2) - Integer.signum(daBest.get(2)));
            nuAntal = 0;
            for (List<Integer> ints : g) {
                int dist = abs(ints.get(0) - daBest.get(0))
                        + abs(ints.get(1) - daBest.get(1))
                        + abs(ints.get(2) - daBest.get(2));
                if (dist <= ints.get(3)) {
                    nuAntal++;
                }
            }
            if (nuAntal > startAntal) {
                startAntal = nuAntal;
            }
            if (nuAntal < startAntal) {
                daBest.set(closer % 3, lastValue);
                stall++;
            }else {
                stall = 0;
            }
        } while (stall<10);
        System.out.println("closer = " + closer);


        int bestDist = Integer.MAX_VALUE;
        int bestAntal = 0;
        List<Integer> bestOfBest = null;
        for (int x = -100; x <= 100; x++) {
            int X = daBest.get(0) + x;
            for (int y = -100; y <= 100; y++) {
                int Y = daBest.get(1) + y;
                for (int z = -100; z <= 100; z++) {
                    int Z = daBest.get(2) + z;
                    int myDist = abs(X) + abs(Y) + abs(Z);
                    //if(myDist > bestDist && bestAntal == 854) continue;
                    int antal = 0;
                    for (List<Integer> ints : g) {
                        int dist = abs(ints.get(0) - X)
                                + abs(ints.get(1) - Y)
                                + abs(ints.get(2) - Z);
                        if (dist <= ints.get(3)) {
                            antal++;
                        }
                    }
                    if (antal > bestAntal || (antal == bestAntal && myDist < bestDist)) {
                        bestOfBest = Arrays.asList(X, Y, Z);
                        bestDist = myDist;
                        bestAntal = antal;
                        System.out.println("bestDist = " + bestDist);
                        System.out.println("bestAntal = " + bestAntal);
                        System.out.println("X = " + X + " " + x);
                        System.out.println("Y = " + Y + " " + y);
                        System.out.println("Z = " + Z + " " + z);
                    }
                }
            }
        }

        int antal = 0;
        for (List<Integer> ints : g) {
            int dist = abs(ints.get(0) - bestOfBest.get(0))
                    + abs(ints.get(1) - bestOfBest.get(1))
                    + abs(ints.get(2) - bestOfBest.get(2));
            if (dist == ints.get(3)) {
                System.out.println("ints = " + ints);
            }
        }
        System.out.println("antal = " + antal);

        if (bestDist == 86012062) {
            System.out.println("Already tried this :-(");
        } else if (bestDist < 86012062) {
            System.out.println("Too low :-(");
        } else {
            System.out.println("Could it work?");
        }
        return String.valueOf(bestDist);
    }
}

//fel
//82265125