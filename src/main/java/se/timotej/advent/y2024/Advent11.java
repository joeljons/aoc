package se.timotej.advent.y2024;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Advent11 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent11().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        List<String> g = new ArrayList<>(Arrays.asList(strs.get(0).split(" ")));
        for (int i = 0; i < 25; i++) {
            List<String> g2 = new ArrayList<>();
            for (String stone : g) {
                if (stone.startsWith("-1")) {
                    throw new RuntimeException(stone);
                }
                if (stone.equals("0")) {
                    g2.add("1");
                } else if (stone.length() % 2 == 0) {
                    String a = stone.substring(0, stone.length() / 2);
                    String b = stone.substring(stone.length() / 2);
                    while (b.charAt(0) == '0' && b.length() > 1) {
                        b = b.substring(1);
                    }
                    g2.add(a);
                    g2.add(b);
                } else {
                    String a = Long.toString(Long.parseLong(stone) * 2024);
                    g2.add(a);
                }
            }
            g = g2;
        }

        return g.size();
    }

}
