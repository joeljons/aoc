package se.timotej.advent.y2018;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Advent7 {

    public static void main(String[] args) throws IOException {
        String svar = new Advent7().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private List<Pair<Integer, Integer>> input = new ArrayList<>();

    private String calc(List<String> strs) {
        char[] pres = new char[strs.size()];
        char[] afters = new char[strs.size()];
        int i=0;
        for (String str : strs) {
            String[] line = str.split(" ");
            char pre = line[1].charAt(0);
            char after = line[7].charAt(0);
            pres[i] = pre;
            afters[i++] = after;
        }
        boolean[] done = new boolean[26];
        String svar = "";
        for(i=0;i<26;i++){
            boolean[] canDo = new boolean[26];
            Arrays.fill(canDo, true);
            for (int j = 0; j < pres.length; j++) {
                char pre = pres[j];
                char after = afters[j];
                if(!done[pre-'A']){
                    canDo[after-'A'] = false;
                }
            }
            for (int j = 0; j < canDo.length; j++) {
                boolean b = canDo[j];
                if(b && !done[j]){
                    svar += (char)(j+'A');
                    done[j] = true;
                    break;
                }
            }

        }
        return svar;
    }

}
