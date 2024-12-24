package se.timotej.advent.y2024;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Advent24 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent24().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        Map<String, Boolean> g = new TreeMap<>();
        List<List<String>> input = Util.splitByDoubleNewline(strs);
        for (String s : input.getFirst()) {
            String[] split = s.split(": ");
            g.put(split[0], split[1].equals("1"));
        }

        boolean missed;
        do {
            missed = false;
            for (String s : input.getLast()) {
                String[] split = s.split(" ");
                if (!g.containsKey(split[4])) {
                    if (g.containsKey(split[0]) && g.containsKey(split[2])) {
                        boolean a = split[1].equals("AND")
                                ? (g.get(split[0]) && g.get(split[2]))
                                : split[1].equals("OR")
                                ? (g.get(split[0]) || g.get(split[2]))
                                : (g.get(split[0]) ^ g.get(split[2]));
                        g.put(split[4], a);
                    } else {
                        missed = true;
                    }
                }
            }
        } while (missed);
        StringBuilder sb = new StringBuilder();
        for (int z = 0; g.containsKey(String.format("z%02d", z)); z++) {
            sb.append(g.get(String.format("z%02d", z)) ? "1" : "0");
        }
        return Long.parseLong(StringUtils.reverse(sb.toString()), 2);
    }

}
