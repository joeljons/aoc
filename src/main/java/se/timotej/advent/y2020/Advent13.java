package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.List;

public class Advent13 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent13().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int time = Integer.parseInt(strs.get(0));
        List<Integer> busLines = Util.findAllInts(strs.get(1));
        int best = Integer.MAX_VALUE;
        int svar = 0;
        for (Integer busLine : busLines) {
            int busCount = time / busLine;
            if (busCount * busLine < time) {
                busCount++;
            }
            int nu = busCount * busLine;
            if (nu < best) {
                best = nu;
                svar = busLine * (nu - time);
            }
        }
        return svar;
    }
}
