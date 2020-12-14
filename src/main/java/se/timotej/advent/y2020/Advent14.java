package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Advent14 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent14().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long sum = 0;
        Map<Long, Long> g = new TreeMap<>();
        String mask = "";
        for (String str : strs) {
            if (str.startsWith("mask = ")) {
                mask = str.substring("mask = ".length());
                continue;
            }
            List<Long> longs = Util.findAllLongs(str);
            g.put(longs.get(0), maska(mask, longs.get(1)));
        }
        for (Long value : g.values()) {
            sum += value;
        }
        return sum;
    }

    private Long maska(String mask, Long value) {
        long sum = 0;
        for (int i = 0; i < 36; i++) {
            if (mask.charAt(35 - i) == '1') {
                sum += (1L<<i);
            } else if (mask.charAt(35 - i) == 'X') {
                if ((value & (1L<<i)) != 0) {
                    sum += (1L<<i);
                }
            }
        }
        return sum;
    }
}
