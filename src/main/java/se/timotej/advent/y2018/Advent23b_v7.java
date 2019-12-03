package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Advent23b_v7 {

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        String svar = new Advent23b_v7().calc(Online.get(23));
        long duration = System.currentTimeMillis()-start;
        System.out.println("duration = " + duration/1000);
        System.out.println("svar = " + svar);
       // Online.submit(svar);
    }

    private static final int LIMIT = 2;

    private String calc(List<String> strs) {
        List<List<Integer>> g = new ArrayList<>();
        for (String str2 : strs) {
            List<Integer> ints = Util.findAllInts(str2);
            g.add(ints);
        }
        int bestDist = Integer.MAX_VALUE;
        int bestAntal = 0;
        int count = 0;
        for (List<Integer> from : g) {
            System.out.println("count = " + count);
            count++;
            for (List<Integer> target : g) {
                if (from == target) {
                    continue;
                }
                double dirx = target.get(0) - from.get(0);
                double diry = target.get(1) - from.get(1);
                double dirz = target.get(2) - from.get(2);
                double theDist = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
                dirx /= theDist;
                diry /= theDist;
                dirz /= theDist;
                double min = 0;
                double max = from.get(3);
                int X,Y,Z;
                do {
//                    System.out.println(min + " - " + max);
                    double mellan = (max + min) / 2;
                    X = (int) (from.get(0) + dirx * mellan);
                    Y = (int) (from.get(1) + diry * mellan);
                    Z = (int) (from.get(2) + dirz * mellan);
                    int nyDist = abs(X - from.get(0)) + abs(Y - from.get(1)) + abs(Z - from.get(2));
//                    System.out.println("nyDist - from.get(3) = " + (nyDist - from.get(3)));
                    if (nyDist < from.get(3)) {
                        min = mellan;
                    } else if (nyDist > from.get(3)) {
                        max = mellan;
                    } else {
                        break;
                    }
                } while (max - min > 1e-6);
                if (abs(X - from.get(0)) + abs(Y - from.get(1)) + abs(Z - from.get(2))-from.get(3)>1) {
                    System.out.println("ey");
                    System.exit(0);
                }else {
//                    System.out.println("yay");
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
        System.out.println("count = " + count);
        return String.valueOf(bestDist);
    }
}

//fel 82265125
//too low 82265126
//rätt 88122632 (906)