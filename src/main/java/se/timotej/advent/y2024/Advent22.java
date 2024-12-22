package se.timotej.advent.y2024;

import java.io.IOException;
import java.util.List;

public class Advent22 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent22().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;

        for (String str : strs) {
            long secret = Long.parseLong(str);
            for (int i = 0; i < 2000; i++) {
                secret = nextSecret(secret);
            }
            svar += secret;
        }

        return svar;
    }

    private static long nextSecret(long secret) {
        secret ^= secret * 64;
        secret %= 16777216;
        secret ^= secret / 32;
        secret %= 16777216;
        secret ^= secret * 2048;
        secret %= 16777216;
        return secret;
    }
}
