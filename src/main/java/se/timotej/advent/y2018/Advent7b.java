package se.timotej.advent.y2018;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Advent7b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent7b().calc(Online.get());
        System.out.println("svar = " + svar);
//        Online.submit(svar);
    }

    private List<Pair<Integer, Integer>> input = new ArrayList<>();

    private int calc(List<String> strs) {
        char[] pres = new char[strs.size()];
        char[] afters = new char[strs.size()];
        int i = 0;
        for (String str : strs) {
            String[] line = str.split(" ");
            char pre = line[1].charAt(0);
            char after = line[7].charAt(0);
            pres[i] = pre;
            afters[i++] = after;
        }
        int t = 0;
        int workers = 5;
        /* -1 not started
            0 done
            >0 in progress
         */
        int[] done = new int[26];
        Arrays.fill(done, -1);
        while (IntStream.of(done).anyMatch(j -> j != 0)) {
            for (i = 0; i < 26; i++) {
                boolean[] canDo = new boolean[26];
                Arrays.fill(canDo, true);
                for (int j = 0; j < pres.length; j++) {
                    char pre = pres[j];
                    char after = afters[j];
                    if (done[pre - 'A'] != 0) {
                        canDo[after - 'A'] = false;
                    }
                }
                for (int j = 0; j < canDo.length; j++) {
                    if (workers > 0 && canDo[j] && done[j] == -1) {
                        done[j] = 61 + j;
                        workers--;
                    }
                }
            }
            boolean completedSomething = false;
            while (!completedSomething) {
                for (int j = 0; j < done.length; j++) {
                    if (done[j] > 0) {
                        done[j]--;
                        if (done[j] == 0) {
                            workers++;
                            completedSomething = true;
                        }
                    }
                }
                t++;
            }
        }
        return t;
    }

}
