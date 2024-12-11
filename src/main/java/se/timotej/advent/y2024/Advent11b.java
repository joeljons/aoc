package se.timotej.advent.y2024;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent11b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent11b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        Map<String, Long> g = new HashMap<>();
        for (String s : strs.get(0).split(" ")) {
            g.merge(s, 1L, Long::sum);
        }
        for (int i = 0; i < 75; i++) {
            Map<String, Long> g2 = new HashMap<>();
            for (Map.Entry<String, Long> entry : g.entrySet()) {
                String stone = entry.getKey();
                Long antal = entry.getValue();
                if (stone.startsWith("-1")) {
                    throw new RuntimeException(stone);
                }
                if (stone.equals("0")) {
                    g2.merge("1", antal, Long::sum);
                } else if (stone.length() % 2 == 0) {
                    String a = stone.substring(0, stone.length() / 2);
                    String b = stone.substring(stone.length() / 2);
                    while (b.charAt(0) == '0' && b.length() > 1) {
                        b = b.substring(1);
                    }
                    g2.merge(a, antal, Long::sum);
                    g2.merge(b, antal, Long::sum);
                } else {
                    String a = Long.toString(Long.parseLong(stone) * 2024);
                    g2.merge(a, antal, Long::sum);
                }
            }
            g = g2;
        }

        return g.values().stream().mapToLong(Long::longValue).sum();
    }

}
