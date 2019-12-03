package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.List;

public class Advent2b {

    public static void main(String[] args) throws IOException {
        List<String> strs = Online.get(2);
        //for (int i = 0; i < 5_000_000; i++) {
//            strs.add(RandomStringUtils.randomAlphabetic(26));
  //      }
    //    Lists.reverse(strs);
        long start = System.nanoTime();
        String svar = new Advent2b().calc(strs);
        long duration = System.nanoTime() - start;
        System.out.println(duration / 1000000);
        System.out.println("svar = " + svar);
        //Online.submit(2, 2, svar);
    }

    private String calc(List<String> strs) {
        for (int i = 0; i < strs.size(); i++) {
            String s1 = strs.get(i);
            for (int j = i + 1; j < strs.size(); j++) {
                String s2 = strs.get(j);
                int diffs = 0;
                int diffPos = -1;
                for (int k = 0; k < s1.length(); k++) {
                    if (s1.charAt(k) != s2.charAt(k)) {
                        diffs++;
                        diffPos = k;
                    }
                }
                if (diffs <= 1) {
                    System.out.println("diffs = " + diffs);
                    return s1.substring(0, diffPos) + s1.substring(diffPos + 1, s1.length());
                }
            }
        }
        return null;
    }

}
