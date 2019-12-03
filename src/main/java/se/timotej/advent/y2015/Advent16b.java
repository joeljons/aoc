package se.timotej.advent.y2015;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Advent16b {
    public static void main(String[] args) throws IOException {
        int svar = new Advent16b().calc(Online.get(16));
        System.out.println("svar = " + svar);
        Online.submit(16, 2, svar);
    }

    int calc(List<String> strs) {
        Map<String, Function<Integer, Boolean>> g = new HashMap<>();
        g.put("children", amount -> amount == 3);
        g.put("cats", amount -> amount > 7);
        g.put("samoyeds", amount -> amount == 2);
        g.put("pomeranians", amount -> amount < 3);
        g.put("akitas", amount -> amount == 0);
        g.put("vizslas", amount -> amount == 0);
        g.put("goldfish", amount -> amount < 5);
        g.put("trees", amount -> amount > 3);
        g.put("cars", amount -> amount == 2);
        g.put("perfumes", amount -> amount == 1);
        for (String str : strs) {
            int matches = 0;
            String[] line = str.split("[ :,]");
            for (int i = 0; i < 3; i++) {
                String what = line[3 + 4 * i];
                int amount = Integer.parseInt(line[5 + 4 * i]);
                System.out.println(what + " " + amount);
                if (g.get(what).apply(amount)) {
                    matches++;
                }
            }
            if (matches == 3) {
                return Integer.parseInt(line[1]);
            }
        }
        return 0;
    }
}
