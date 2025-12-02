package se.timotej.advent.y2025;

import java.io.IOException;
import java.util.List;

public class Advent2b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent2b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;
        for (String interval : strs.get(0).split(",")) {
            List<Long> allLongs = Util.findAllPositiveLongs(interval);
            long from = allLongs.get(0);
            long to = allLongs.get(1);
            for (long i = from; i <= to; i++) {
                if (invalid(i)) {
                    svar += i;
                }
            }
        }
        return svar;
    }

    private boolean invalid(long i) {
        String s = String.valueOf(i);
        int len = s.length();
        for (int repeatLen = 1; repeatLen <= len / 2; repeatLen++) {
            if (len % repeatLen == 0) {
                boolean match = true;
                String pattern = s.substring(0, repeatLen);
                for (int j = 1; j < len/repeatLen; j++) {
                    if (!pattern.equals(s.substring(j * repeatLen, (j + 1) * repeatLen))) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    return true;
                }
            }
        }
        return false;
    }
}
