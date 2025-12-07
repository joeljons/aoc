package se.timotej.advent.y2025;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;

public class Advent6b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent6b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;
        String[] input = new String[strs.getFirst().length()];
        for (int x = 0; x < input.length; x++) {
            String num = "";
            for (int y = 0; y < strs.size() - 1; y++) {
                num += strs.get(y).charAt(x);
            }
            input[x] = num;
        }
        for (int x = 0; x < input.length; x++) {
            boolean plus = strs.getLast().charAt(x) == '+';
            Long now = Long.parseLong(input[x].trim());
            for (x++; x < input.length && StringUtils.isNotBlank(input[x]); x++) {
                if (plus) {
                    now += Long.parseLong(input[x].trim());
                } else {
                    now *= Long.parseLong(input[x].trim());
                }
            }
            svar += now;

        }
        return svar;
    }
}
