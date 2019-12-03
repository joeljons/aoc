package se.timotej.advent.y2015;

import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class Advent7b {
    public static void main(String[] args) throws IOException {
        int svar = new Advent7b().calc(Online.get(7));
        System.out.println("svar = " + svar);
        Online.submit(7, 2, svar);
    }

    Map<String, Supplier<Integer>> g = new HashMap<>();
    Map<String, Integer> cache = new HashMap<>();
    Map<String, BiFunction<Integer, Integer, Integer>> operators = new HashMap<>();

    private int get(String var) {
        if (cache.containsKey(var)) {
            return cache.get(var);
        } else {
            int val = g.get(var).get();
            cache.put(var, val);
            return val;
        }
    }

    private Supplier<Integer> createProvider(String str) {
        if (NumberUtils.isCreatable(str)) {
            int val = Integer.parseInt(str);
            return () -> val;
        } else {
            return () -> get(str);
        }
    }

    private int calc(List<String> strs) {
        operators.put("AND", (a, b) -> a & b);
        operators.put("OR", (a, b) -> a | b);
        operators.put("LSHIFT", (a, b) -> 0xFFFF & (a << b));
        operators.put("RSHIFT", (a, b) -> 0xFFFF & (a >> b));

        for (String str : strs) {
            String[] line = str.split(" ");
            if (line.length == 3) {
                // Simple assign
                g.put(line[2], createProvider(line[0]));
            } else if (line.length == 4) {
                // NOT
                g.put(line[3], () -> 0xFFFF & ~createProvider(line[1]).get());
            } else if (line.length == 5) {
                BiFunction<Integer, Integer, Integer> operator = operators.get(line[1]);
                g.put(line[4], () -> operator.apply(createProvider(line[0]).get(), createProvider(line[2]).get()));
            } else {
                throw new RuntimeException("WAT? " + str);
            }

        }
        cache.put("b", new Advent7().calc(strs));
        return g.get("a").get();
    }
}
