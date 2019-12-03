package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.List;

public class Advent23 {

    public static void main(String[] args) throws IOException {
        String svar = new Advent23().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private String calc(List<String> strs) {
        int svar = 0;

        List<Integer> strongest = null;
        for (String str : strs) {
            List<Integer> ints = Util.findAllInts(str);
            if(strongest == null || ints.get(3) > strongest.get(3))strongest = ints;
        }
        for (String str : strs) {
            List<Integer> ints = Util.findAllInts(str);
            int dist = Math.abs(ints.get(0) - strongest.get(0))
                    + Math.abs(ints.get(1) - strongest.get(1))
                    + Math.abs(ints.get(2) - strongest.get(2));
            if(dist<= strongest.get(3))svar++;
        }


        return String.valueOf(svar);
    }
}

