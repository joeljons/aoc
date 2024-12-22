package se.timotej.advent.y2024;

import java.io.IOException;
import java.util.*;

public class Advent22bFaster {

    public static void main(String[] args) throws IOException {
        long svar = new Advent22bFaster().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        Map<List<Integer>, Integer> diff2Price = new HashMap<>();
        for (int i = 0; i < strs.size(); i++) {
            long secret = Long.parseLong(strs.get(i));
            List<Integer> last5 = new ArrayList<>();
            Set<List<Integer>> seen = new HashSet<>();
            for (int j = 0; j < 2001; j++) {
                last5.add((int) (secret % 10));
                if (last5.size() > 5) {
                    last5.removeFirst();
                }
                if (last5.size() == 5) {
                    List<Integer> key = List.of(last5.get(1) - last5.get(0), last5.get(2) - last5.get(1), last5.get(3) - last5.get(2), last5.get(4) - last5.get(3));
                    if (seen.add(key)) {
                        diff2Price.merge(key, (int) (secret % 10), Integer::sum);
                    }
                }
                secret = nextSecret(secret);
            }
        }
        return diff2Price.values().stream().mapToInt(Integer::intValue).max().getAsInt();
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
