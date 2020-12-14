package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Advent14b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent14b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    Map<Long, Long> g = new TreeMap<>();
    String mask = "";

    private long calc(List<String> strs) {
        long sum = 0;
        for (String str : strs) {
            if (str.startsWith("mask = ")) {
                mask = str.substring("mask = ".length());
                continue;
            }
            List<Long> longs = Util.findAllLongs(str);
            putta(longs.get(0), longs.get(1), 0, 0);
        }
        for (Long value : g.values()) {
            sum += value;
        }
        return sum;
    }

    private void putta(Long address, Long result, int i, long curValue) {
        if (i == 36) {
            g.put(curValue, result);
            return;
        }
        if (mask.charAt(35 - i) == '0') {
            if ((address & (1L<<i)) != 0) {
                putta(address, result, i + 1, curValue + (1L<<i));
            } else {
                putta(address, result, i + 1, curValue);
            }
        } else if (mask.charAt(35 - i) == '1') {
            putta(address, result, i + 1, curValue + (1L<<i));
        } else {
            putta(address, result, i + 1, curValue + (1L<<i));
            putta(address, result, i + 1, curValue);
        }
    }
}
