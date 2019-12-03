package se.timotej.advent.y2018;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.util.List;

public class Advent2b_v3 {

    public static void main(String[] args) throws IOException {
        List<String> strs = Online.get(2);
        for (int i = 0; i < 5_000_000; i++) {
            strs.add(RandomStringUtils.randomAlphabetic(26).toLowerCase());
        }
        Lists.reverse(strs);
        long start = System.nanoTime();
        String svar = new Advent2b_v3().calc(strs);
        long duration = System.nanoTime() - start;
        System.out.println(duration / 1000000);
        System.out.println("svar = " + svar);
        //Online.submit(2, 2, svar);
    }

    private String calc(List<String> strs) {
        int len = strs.get(0).length();
        for (int p = 0; p < len; p++) {

            for (String str : strs) {
                //rot.add(str, 0, p);
            }
        }
        return null;

    }
}
