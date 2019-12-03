package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Advent23b_v6b {

    public static void main(String[] args) throws IOException {
        String svar = new Advent23b_v6b().calc(Online.get(23));
        System.out.println("svar = " + svar);
//        Online.submit(svar);
    }

    private String calc(List<String> strs) {
        List<List<Integer>> g = new ArrayList<>();
        for (String str2 : strs) {
            g.add(Util.findAllInts(str2));
        }
//        int lastx = 11237642;
//        int lasty = 28237642;
//        int lastz = 47237642;

//        int lastx = 10707642;
//        int lasty = 27987642;
//        int lastz = 47317642;

//        int lastx = 10700542;
//        int lasty = 27993742;
//        int lastz = 47317842;

        int lastx = 10706642;
        int lasty = 27987612;
        int lastz = 47317812;

        int lastdelta = 100;
        int[] min = new int[]{lastx - lastdelta, lasty - lastdelta, lastz - lastdelta};
        int[] max = new int[]{lastx + lastdelta, lasty + lastdelta, lastz + lastdelta};
        int delta = 1;

        int bestDist = Integer.MAX_VALUE;
        int bestAntal = 0;
        int count = 0;
        for (int x = min[0]; x <= max[0]; x += delta) {
            for (int y = min[1]; y <= max[1]; y += delta) {
                for (int z = min[2]; z <= max[2]; z += delta) {
                    count++;
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
        System.out.println("count = " + count);

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

//fel 82265125
//too low 82265126
//too low 86012062