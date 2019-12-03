package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.abs;

public class Advent23b_v6 {

    public static void main(String[] args) throws IOException {
        String svar = new Advent23b_v6().calc(Online.get(23));
        System.out.println("svar = " + svar);
//        Online.submit(svar);
    }

    private String calc(List<String> strs) {
        List<List<Integer>> g = new ArrayList<>();
        int[] max = new int[3];
        int[] min = new int[3];
        Arrays.fill(max, Integer.MIN_VALUE);
        Arrays.fill(min, Integer.MAX_VALUE);
        for (String str2 : strs) {
            List<Integer> ints = Util.findAllInts(str2);
            g.add(ints);
            for (int i = 0; i < 3; i++) {
                max[i] = Math.max(max[i], ints.get(i));
                min[i] = Math.min(min[i], ints.get(i));
            }
        }
        int delta = 1000000;
        for (int i = 0; i < 3; i++) {
            System.out.println(max[i] - min[i]);
        }
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
        return String.valueOf(bestDist);
    }
}

//fel 82265125
//too low 82265126