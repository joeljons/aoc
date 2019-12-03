package se.timotej.advent.y2018;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Advent6 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent6().calc(Online.get());
        System.out.println("svar = " + svar);
//        Online.submit(svar);
    }

    private List<Pair<Integer, Integer>> input = new ArrayList<>();

    private int calc(List<String> strs) {
        int max = 0;
        for (String str : strs) {
            String[] line = str.split("[, ]+");
            int x = Integer.parseInt(line[0]);
            int y = Integer.parseInt(line[1]);
            max = Math.max(max, Math.max(x, y));
            input.add(Pair.of(x, y));
        }
        int[] closestCount = new int[strs.size()];
        for (int y = 0; y <= max; y++) {
            for (int x = 0; x <= max; x++) {
                int closest = getClosest(x, y);
                if (closest == -1) {
                    continue;
                }
                if (y == 0 || x == 0 || y == max || x == max) {
                    closestCount[closest] = Integer.MIN_VALUE;
                }
                closestCount[closest]++;
            }
        }
        return IntStream.of(closestCount).max().getAsInt();
    }

    private int getClosest(int x, int y) {
        int closestDist = Integer.MAX_VALUE;
        int closestI = 0;
        boolean lika = false;
        for (int i = 0; i < input.size(); i++) {
            int dist = Math.abs(x - input.get(i).getLeft()) + Math.abs(y - input.get(i).getRight());
            if (dist < closestDist) {
                lika = false;
                closestDist = dist;
                closestI = i;
            } else if (dist == closestDist) {
                lika = true;
            }
        }
        if (lika) {
            return -1;
        }
        return closestI;
    }

}
