package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Advent15b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent15b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        List<Integer> g = Util.findAllInts(strs.get(0));
        int[] lastTime = new int[30000000];
        int[] lastTime2 = new int[30000000];
        int next = 0;
        for (int i = 1; i <= g.size(); i++) {
            next = g.get(i-1);
            lastTime[next] = i;
        }

        for (int i = g.size()+1; i <= 30000000; i++) {
            int last = lastTime[next];
            int last2 = lastTime2[next];
            if (last2 == 0) {
                next = 0;
            } else {
                next = last - last2;
            }
            int apa = lastTime[next];
            if (apa != 0) {
                lastTime2[next] = apa;
            }

            lastTime[next] = i;
        }
        return next;
    }

}
