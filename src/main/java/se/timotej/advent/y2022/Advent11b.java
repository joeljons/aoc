package se.timotej.advent.y2022;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class Advent11b {
    public static void main(String[] args) throws IOException {
        long svar = new Advent11b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    static BigInteger totalMod = BigInteger.ONE;

    private long calc(List<String> strs) {
        List<Monkey> monkeys = new ArrayList<>();
        for (int i = 0; i < strs.size(); i++) {
            String str = strs.get(i);
            if (str.contains("Starting items:")) {
                String opStr = strs.get(i + 1).replace("  Operation: new = old ", "");
                Function<BigInteger, BigInteger> op;
                if (opStr.startsWith("* old")) {
                    op = (item) -> item.multiply(item);
                } else if (opStr.startsWith("* ")) {
                    Long multiplier = Util.findAllLongs(opStr).get(0);
                    BigInteger bigMultipler = BigInteger.valueOf(multiplier);
                    op = (item) -> item.multiply(bigMultipler);
                } else if (opStr.startsWith("+ ")) {
                    Long multiplier = Util.findAllLongs(opStr).get(0);
                    BigInteger bigMultipler = BigInteger.valueOf(multiplier);
                    op = (item) -> item.add(bigMultipler);
                } else {
                    throw new RuntimeException(opStr);
                }
                Long test = Util.findAllLongs(strs.get(i + 2)).get(0);
                totalMod = totalMod.multiply(BigInteger.valueOf(test));
                monkeys.add(new Monkey(
                        Util.findAllLongs(str),
                        op,
                        test,
                        Util.findAllInts(strs.get(i + 3)).get(0),
                        Util.findAllInts(strs.get(i + 4)).get(0)));
            }
        }
        for (int round = 0; round < 10000; round++) {
            for (Monkey monkey : monkeys) {
                monkey.execute(monkeys);
            }
        }
        List<Long> monkeyInspections = new ArrayList<>();
        for (Monkey monkey : monkeys) {
            monkeyInspections.add(monkey.inspections);
        }
        Collections.sort(monkeyInspections);
        return monkeyInspections.get(monkeyInspections.size() - 1) * monkeyInspections.get(monkeyInspections.size() - 2);
    }

    private static class Monkey {
        Function<BigInteger, BigInteger> operation;
        List<BigInteger> items;
        long test;
        int throwIfTrue;
        int throwIfFalse;
        long inspections;

        public Monkey(List<Long> items, Function<BigInteger, BigInteger> operation, long test, int throwIfTrue, int throwIfFalse) {
            this.operation = operation;
            this.items = new ArrayList<>();
            for (Long item : items) {
                this.items.add(BigInteger.valueOf(item));
            }
            this.test = test;
            this.throwIfTrue = throwIfTrue;
            this.throwIfFalse = throwIfFalse;
        }

        public void execute(List<Monkey> monkeys) {
            for (BigInteger item : items) {
                inspections++;
                item = operation.apply(item).remainder(totalMod);
                int target = item.remainder(BigInteger.valueOf(test)).equals(BigInteger.ZERO) ? throwIfTrue : throwIfFalse;
                monkeys.get(target).items.add(item);
            }
            items.clear();
        }
    }
}
