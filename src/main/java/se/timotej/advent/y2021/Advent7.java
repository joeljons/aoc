package se.timotej.advent.y2021;

import java.io.IOException;
import java.util.List;

public class Advent7 {

    public static void main(String[] args) throws IOException {
        var svar = new Advent7().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        List<Integer> crabs = Util.findAllInts(strs.get(0));
        int max = crabs.stream().mapToInt(Integer::intValue).max().getAsInt();
        int best = Integer.MAX_VALUE;
        for (int i = 0; i <= max; i++) {
            int sum = 0;
            for (Integer crab : crabs) {
                sum += Math.abs(crab - i);
            }
            if (sum < best) {
                best = sum;
            }
        }
        return best;
    }

}
