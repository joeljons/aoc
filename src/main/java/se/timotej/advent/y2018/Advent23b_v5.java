package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.abs;

public class Advent23b_v5 {

    public static void main(String[] args) throws IOException {
        String svar = new Advent23b_v5().calc(Online.get(23));
        System.out.println("svar = " + svar);
//        Online.submit(svar);
    }

    private String calc(List<String> strs) {

        int best = 0;
        List<List<Integer>> g = new ArrayList<>();
        for (String str2 : strs) {
            g.add(Util.findAllInts(str2));
        }

        int startNr = 0;

        int bestest = Integer.MAX_VALUE;
        int bestestAntal = 0;
        for (List<Integer> daBest : g.subList(750,1000)) {
            daBest = new ArrayList<>(daBest);
            System.out.println("startNr = " + startNr);
            startNr++;

            int startAntal = 0;
            for (List<Integer> ints : g) {
                int dist = Math.abs(ints.get(0) - daBest.get(0))
                        + Math.abs(ints.get(1) - daBest.get(1))
                        + Math.abs(ints.get(2) - daBest.get(2));
                if (dist <= ints.get(3)) {
                    startAntal++;
                }
            }
            int nuAntal;
            int closer = 0;
            int stall = 0;
            int[] size = new int[]{1<<20, 1<<20, 1<<20};
            do {
                closer++;
                if (size[closer % 3] == 0) {
                    continue;
                }
                Integer lastValue = daBest.get(closer % 3);
                daBest.set(closer % 3, lastValue - size[closer%3] * Integer.signum(daBest.get(closer % 3)));
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
                    size[closer % 3] /= 2;
                }
            } while (size[0] + size[1] + size[2] > 0);
            System.out.println("closer = " + closer);


            List<Integer> bestOfBest = null;

            int bestDist = Integer.MAX_VALUE;
            int bestAntal = 0;
            for (int x = -100; x <= 100; x++) {
                int X = daBest.get(0) + x;
                for (int y = -100; y <= 100; y++) {
                    int Y = daBest.get(1) + y;
                    for (int z = -100; z <= 100; z++) {
                        int Z = daBest.get(2) + z;
                        int myDist = Math.abs(X) + Math.abs(Y) + Math.abs(Z);
//                    if(myDist > bestDist && bestAntal == 854) continue;
                        int antal = 0;
                        for (List<Integer> ints : g) {
                            int dist = Math.abs(ints.get(0) - X)
                                    + Math.abs(ints.get(1) - Y)
                                    + Math.abs(ints.get(2) - Z);
                            if (dist <= ints.get(3)) {
                                antal++;
                            }
                        }
                        if (antal > bestAntal || (antal == bestAntal && myDist < bestDist)) {
                            bestDist = myDist;
                            bestAntal = antal;
                          /*  System.out.println("bestDist = " + bestDist);
                            System.out.println("bestAntal = " + bestAntal);
                            System.out.println("X = " + X + " " + x);
                            System.out.println("Y = " + Y + " " + y);
                            System.out.println("Z = " + Z + " " + z);*/
                            bestOfBest = Arrays.asList(X, Y, Z);
                        }
                    }
                }
            }

/*
            int antal = 0;
            for (List<Integer> ints : g) {
                int dist = abs(ints.get(0) - bestOfBest.get(0))
                        + abs(ints.get(1) - bestOfBest.get(1))
                        + abs(ints.get(2) - bestOfBest.get(2));
                if (dist == ints.get(3)) {
                    System.out.println("ints = " + ints);
                }
            }
            System.out.println("antal = " + antal);*/
            if (bestAntal > bestestAntal || (bestAntal == bestestAntal && bestDist < bestest)) {
                bestestAntal = bestAntal;
                bestest = bestDist;
                System.out.println("bestAntal = " + bestAntal);
                System.out.println("bestest = " + bestest);
            }
        }


        return String.valueOf(bestest);
    }
}

//fel 82265125
//too low 82265126
