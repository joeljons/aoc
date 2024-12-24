package se.timotej.advent.y2024;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Advent24b {

    public static final int BIT_LIMIT = 50;

    public static void main(String[] args) throws IOException {
        String svar = new Advent24b().calc(Online.get(24));
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private Random r = new Random();
    private volatile Pair<String, String> foundSwap;

    private String calc(List<String> strs) {
        Set<String> g = new HashSet<>();
        List<List<String>> input = Util.splitByDoubleNewline(strs);
        for (String s : input.getFirst()) {
            String[] split = s.split(": ");
            g.add(split[0]);
        }

        List<String> allOutputs = new ArrayList<>();
        List<Wire> wires = new ArrayList<>();
        for (String s : input.getLast()) {
            String[] split = s.split(" ");
            allOutputs.add(split[4]);
            wires.add(new Wire(split[0], split[2],
                    split[1].equals("AND")
                            ? (a, b) -> a && b
                            : split[1].equals("OR")
                            ? (a, b) -> a || b
                            : (a, b) -> a ^ b
                    , split[4]));
        }
        HashMap<String, String> swapMap = new HashMap<>();
        while (true) {
            if (foundSwap != null) {
                swapMap.put(foundSwap.getLeft(), foundSwap.getRight());
                swapMap.put(foundSwap.getRight(), foundSwap.getLeft());
            }
            System.out.println("swapMap = " + swapMap);
            int limit = calcLowestWrongBit(g, wires, swapMap);
            System.out.println("limit = " + limit);
            if (limit == BIT_LIMIT) {
                break;
            }
            foundSwap = null;
            IntStream.range(0, allOutputs.size()).parallel().forEach(a0 -> {
                if (swapMap.containsKey(allOutputs.get(a0)) || foundSwap != null) {
                    return;
                }
                for (int a1 = a0 + 1; a1 < allOutputs.size(); a1++) {
                    if (swapMap.containsKey(allOutputs.get(a1))) {
                        continue;
                    }
                    HashMap<String, String> newSwapMap = new HashMap<>(swapMap);
                    newSwapMap.put(allOutputs.get(a0), allOutputs.get(a1));
                    newSwapMap.put(allOutputs.get(a1), allOutputs.get(a0));
                    if (calcLowestWrongBit(g, wires, newSwapMap) > limit) {
                        System.out.println("newSwapMap = " + newSwapMap);
                        System.out.println("calcLowestWrongBit(g, wires, newSwapMap) = " + calcLowestWrongBit(g, wires, newSwapMap));
                        foundSwap = Pair.of(allOutputs.get(a0), allOutputs.get(a1));
                    }
                }
            });
        }
        return StringUtils.join(new TreeSet<>(swapMap.keySet()), ",");
    }

    private int calcLowestWrongBit(Set<String> g, List<Wire> wires, Map<String, String> swapMap) {
        long wrongBits = 0;
        for (int tries = 0; tries < 100; tries++) {
            Map<String, Boolean> g2 = new TreeMap<>();
            for (String key : g) {
                g2.put(key, r.nextBoolean());
            }
            wrongBits |= addsUp(g2, wires, swapMap);
        }
        long bit = 1;
        int lowestWrongBit = BIT_LIMIT;
        for (int i = 0; i < BIT_LIMIT; i++) {
            if ((wrongBits & bit) != 0) {
                lowestWrongBit = i;
                break;
            }
            bit *= 2;
        }
        return lowestWrongBit;
    }

    private long addsUp(Map<String, Boolean> g, List<Wire> wires, Map<String, String> swapMap) {
        boolean missed;
        boolean addedAny;
        do {
            missed = false;
            addedAny = false;
            for (Wire wire : wires) {
                if (!g.containsKey(getOutput(wire.output, swapMap))) {
                    if (g.containsKey(wire.op1) && g.containsKey(wire.op2)) {
                        g.put(getOutput(wire.output, swapMap), wire.operator.evaluate(g.get(wire.op1), g.get(wire.op2)));
                        addedAny = true;
                    } else {
                        missed = true;
                    }
                }
            }
            if (!addedAny) {
                return -1;
            }
        } while (missed);

        long x = getNumber(g, "x");
        long y = getNumber(g, "y");
        long z = getNumber(g, "z");
        long expected = x + y;
        long bit = 1;
        long wrong = 0;
        for (int i = 0; i < 50; i++) {
            if ((expected & bit) != (z & bit)) {
                wrong |= bit;
            }
            bit *= 2;
        }
        return wrong;
    }

    private static String getOutput(String output, Map<String, String> splits) {
        return splits.getOrDefault(output, output);
    }

    private static long getNumber(Map<String, Boolean> g, String letter) {
        StringBuilder sb = new StringBuilder();
        for (int z = 0; g.containsKey(String.format(letter + "%02d", z)); z++) {
            sb.append(g.get(String.format(letter + "%02d", z)) ? "1" : "0");
        }
        return Long.parseLong(StringUtils.reverse(sb.toString()), 2);
    }

    private record Wire(String op1, String op2, Operator operator, String output) {
    }

    @FunctionalInterface
    private interface Operator {
        boolean evaluate(boolean op1, boolean op2);
    }

}
