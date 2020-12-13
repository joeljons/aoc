package se.timotej.advent.y2020;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class Advent13bModInverse {

    public static void main(String[] args) throws IOException {
        long svar = new Advent13bModInverse().calc(Online.get(13));
        System.out.println("svar = " + svar);
//        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        String[] split = strs.get(1).split(",");
        long t = 0;
        long factor = 1;
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            if (!s.equals("x")) {
                int b = Integer.parseInt(s);
                t += ((BigInteger.valueOf(factor).modInverse(BigInteger.valueOf(b)).longValue() * -(t + i)) % b + b) % b * factor;
                factor *= b;
            }
        }
        return t;
    }
}
