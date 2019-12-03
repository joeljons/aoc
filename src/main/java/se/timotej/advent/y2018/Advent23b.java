package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.abs;

public class Advent23b {

    public static void main(String[] args) throws IOException {
        String svar = new Advent23b().calc(Online.get(23));
        System.out.println("svar = " + svar);
//        Online.submit(svar);
    }

    private String calc(List<String> strs) {

        int best = 0;
        List<List<Integer>> g = new ArrayList<>();
        for (String str2 : strs) {
            g.add(Util.findAllInts(str2));
        }
/*
        List<Integer> daBest = null;
        for (List<Integer> strongest : g) {
            int svar = 0;
            for (List<Integer> ints: g) {
                int dist = Math.abs(ints.get(0) - strongest.get(0))
                        + Math.abs(ints.get(1) - strongest.get(1))
                        + Math.abs(ints.get(2) - strongest.get(2));
                if (dist <= ints.get(3)) {
                    svar++;
                }
            }
            if (svar > best) {
                best = svar;
                System.out.println(svar);
                daBest = strongest;
            }
        }

        daBest = new ArrayList<>(daBest);*/
        List<Integer> daBest = Arrays.asList(
                9885825,
                25878398,
                46500903);
        int startAntal = 0;
        for (List<Integer> ints: g) {
            int dist = Math.abs(ints.get(0) - daBest.get(0))
                    + Math.abs(ints.get(1) - daBest.get(1))
                    + Math.abs(ints.get(2) - daBest.get(2));
            if (dist <= ints.get(3)) {
                startAntal++;
            }
        }
        int nuAntal;
        int closer = 0;
        int stall =0;
        do {
            System.out.println("Come closer! " + closer++);
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
            if(nuAntal < startAntal){
                daBest.set(closer % 3, lastValue);
                stall++;
            } else {
                stall = 0;
            }
        } while (stall<10);

        /*daBest.set(0, daBest.get(0) - 250);
        daBest.set(2, daBest.get(2) + 100);
        */


        List<Integer> bestOfBest = null;

        int bestDist = Integer.MAX_VALUE;
        int bestAntal = 0;
        for(int x=-200;x<=200;x++){
            int X = daBest.get(0)+x;
            for(int y=-200;y<=200;y++){
                int Y = daBest.get(1)+y;
                for(int z=-200;z<=200;z++){
                    int Z = daBest.get(2)+z;
                    int myDist = Math.abs(X)+Math.abs(Y)+Math.abs(Z);
//                    if(myDist > bestDist && bestAntal == 854) continue;
                    int antal = 0;
                    for (List<Integer> ints: g) {
                        int dist = Math.abs(ints.get(0) - X)
                                + Math.abs(ints.get(1) - Y)
                                + Math.abs(ints.get(2) - Z);
                        if (dist <= ints.get(3)) {
                            antal++;
                        }
                    }
                    if(antal>bestAntal || (antal==bestAntal && myDist<bestDist)){
                        bestDist = myDist;
                        bestAntal = antal;
                        System.out.println("bestDist = " + bestDist);
                        System.out.println("bestAntal = " + bestAntal);
                        System.out.println("X = " + X + " " +x);
                        System.out.println("Y = " + Y + " " + y) ;
                        System.out.println("Z = " + Z + " " + z);
                        bestOfBest = Arrays.asList(X, Y, Z);

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



        return String.valueOf(bestDist);
    }
}

//fel 82265125
//too low 82265126