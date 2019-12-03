package se.timotej.advent.y2018;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent12b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent12b().calc(Online.get());
        System.out.println("svar = " + svar);
//        Online.submit(svar);
    }


    private long calc(List<String> strs) throws FileNotFoundException {
        long svar = 0;
        String[] firstLine = strs.get(0).split(" ");
        String initial = firstLine[2];
//        System.out.println("initial = " + initial);
        Map<String, Character> translate = new HashMap<>();
        for (String str : strs.subList(2, strs.size())) {
            String[] line = str.split(" => ");
            String from = line[0];
            String to = line[1];
//            System.out.println("from = " + from);
//            System.out.println("to = " + to);
            translate.put(from, to.charAt(0));
        }
        char[] g = new char[2000];
        Arrays.fill(g, '.');
        for (int i = 0; i < initial.length(); i++) {
            char c = initial.charAt(i);
            g[i + 1000] = c;

        }
        for (int i = 0; i < 200; i++) {
            char[] next = new char[2000];
            for (int j = 2; j < g.length-2; j++) {
                StringBuilder sb = new StringBuilder();
                sb.append(g[j - 2]);
                sb.append(g[j - 1]);
                sb.append(g[j ]);
                sb.append(g[j + 1]);
                sb.append(g[j + 2]);
                String key = sb.toString();
                Character orDefault = translate.getOrDefault(key, '.');
                next[j] = orDefault;
            }
            g = next;
        }
        for (int i = 100; i < g.length-100; i++) {
            char c = g[i];
            if (c == '#') {
                svar += i - 1000+(50000000000L-200);
            }

        }
        return svar;
    }
}
