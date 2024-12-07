package se.timotej.advent.y2024;

import java.io.IOException;
import java.util.List;

public class Advent7b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent7b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;

        for (String str : strs) {
            List<Long> ints = Util.findAllLongs(str);
            if (calibrationOk(ints, ints.get(1), 2)) {
                svar += ints.get(0);
            }
        }

        return svar;
    }

    private boolean calibrationOk(List<Long> ints, long sum, int i) {
        if (i == ints.size()) {
            return sum == ints.get(0);
        }
        long concatenated;
        if (ints.get(i) >= 100) {
            concatenated = 1000 * sum + ints.get(i);
        } else if (ints.get(i) >= 10) {
            concatenated = 100 * sum + ints.get(i);
        } else {
            concatenated = 10 * sum + ints.get(i);
        }
        return calibrationOk(ints, sum + ints.get(i), i + 1)
                || calibrationOk(ints, sum * ints.get(i), i + 1)
                || calibrationOk(ints, concatenated, i + 1);
    }
}
