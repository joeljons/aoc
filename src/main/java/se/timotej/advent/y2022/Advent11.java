package se.timotej.advent.y2022;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class Advent11 {
    public static void main(String[] args) throws IOException {
        long svar = new Advent11().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        List<Monkey> monkeys = new ArrayList<>();
        for (int i = 0; i < strs.size(); i++) {
            String str = strs.get(i);
            if (str.contains("Starting items:")) {
                String opStr = strs.get(i + 1).replace("  Operation: new = old ", "");
                Function<Long, Long> op;
                if (opStr.startsWith("* old")) {
                    op = (item) -> item * item;
                } else if (opStr.startsWith("* ")) {
                    Long multiplier = Util.findAllLongs(opStr).get(0);
                    op = (item) -> item * multiplier;
                } else if (opStr.startsWith("+ ")) {
                    Long multiplier = Util.findAllLongs(opStr).get(0);
                    op = (item) -> item + multiplier;
                } else {
                    throw new RuntimeException(opStr);
                }
                monkeys.add(new Monkey(
                        Util.findAllLongs(str),
                        op,
                        Util.findAllLongs(strs.get(i + 2)).get(0),
                        Util.findAllInts(strs.get(i + 3)).get(0),
                        Util.findAllInts(strs.get(i + 4)).get(0)));
            }
        }
        for (int round = 0; round < 20; round++) {
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
        Function<Long, Long> operation;
        List<Long> items;
        long test;
        int throwIfTrue;
        int throwIfFalse;
        long inspections;

        public Monkey(List<Long> items, Function<Long, Long> operation, long test, int throwIfTrue, int throwIfFalse) {
            this.operation = operation;
            this.items = items;
            this.test = test;
            this.throwIfTrue = throwIfTrue;
            this.throwIfFalse = throwIfFalse;
        }

        public void execute(List<Monkey> monkeys) {
            for (Long item : items) {
                inspections++;
                item = operation.apply(item) / 3;
                int target = item % test == 0 ? throwIfTrue : throwIfFalse;
                monkeys.get(target).items.add(item);
            }
            items.clear();
        }
    }
}
