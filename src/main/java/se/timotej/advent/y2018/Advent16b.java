package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Advent16b {

    public static void main(String[] args) throws IOException {
        String svar = new Advent16b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int reg[] = new int[4];

    private String calc(List<String> strs) {
        List<Consumer<List<Integer>>> instructions = new ArrayList<>();
        instructions.add(in -> { // addr 0
            reg[in.get(3)] = reg[in.get(1)] + reg[in.get(2)];
        });
        instructions.add(in -> { // addi 1
            reg[in.get(3)] = reg[in.get(1)] + in.get(2);
        });


        instructions.add(in -> { // mulr 2
            reg[in.get(3)] = reg[in.get(1)] * reg[in.get(2)];
        });
        instructions.add(in -> { // muli 3
            reg[in.get(3)] = reg[in.get(1)] * in.get(2);
        });


        instructions.add(in -> { // banr 4
            reg[in.get(3)] = reg[in.get(1)] & reg[in.get(2)];
        });
        instructions.add(in -> { // bani 5
            reg[in.get(3)] = reg[in.get(1)] & in.get(2);
        });


        instructions.add(in -> { // borr 6
            reg[in.get(3)] = reg[in.get(1)] | reg[in.get(2)];
        });
        instructions.add(in -> { // bori 7
            reg[in.get(3)] = reg[in.get(1)] | in.get(2);
        });


        instructions.add(in -> { // setr 8
            reg[in.get(3)] = reg[in.get(1)];
        });
        instructions.add(in -> { // seti 9
            reg[in.get(3)] = in.get(1);
        });


        instructions.add(in -> { // gtir 10
            reg[in.get(3)] = in.get(1) > reg[in.get(2)] ? 1 : 0;
        });
        instructions.add(in -> { // gtri 11
            reg[in.get(3)] = reg[in.get(1)] > in.get(2) ? 1 : 0;
        });
        instructions.add(in -> { // gtrr 12
            reg[in.get(3)] = reg[in.get(1)] > reg[in.get(2)] ? 1 : 0;
        });


        instructions.add(in -> { // eqir 13
            reg[in.get(3)] = in.get(1) == reg[in.get(2)] ? 1 : 0;
        });
        instructions.add(in -> { // eqri 14
            reg[in.get(3)] = reg[in.get(1)] == in.get(2) ? 1 : 0;
        });
        instructions.add(in -> { // eqrr 15
            reg[in.get(3)] = reg[in.get(1)] == reg[in.get(2)] ? 1 : 0;
        });

        boolean[][] kaputt = new boolean[16][16];

        int apa;
        for (apa = 0; apa < strs.size(); apa += 4) {
            List<Integer> before = Util.findAllInts(strs.get(apa));
            if (before.isEmpty()) {
                break;
            }
            List<Integer> op = Util.findAllInts(strs.get(apa + 1));
            List<Integer> after = Util.findAllInts(strs.get(apa + 2));

            int ii = 0;
            for (Consumer<List<Integer>> instruction : instructions) {
                for (int i = 0; i < 4; i++) {
                    reg[i] = before.get(i);
                }
                instruction.accept(op);
                for (int i = 0; i < 4; i++) {
                    if (reg[i] != after.get(i)) {
                        kaputt[op.get(0)][ii] = true;
                    }
                }
                ii++;
            }
        }

        boolean somethingFixed;
        do {
            somethingFixed = false;
            for (int i = 0; i < 16; i++) {
                int antalMatch = 0;
                int vilkenMatch = -1;
                for (int j = 0; j < 16; j++) {
                    if (!kaputt[i][j]) {
                        antalMatch++;
                        vilkenMatch = j;
                    }
                }
                if (antalMatch == 1) {
                    for (int j = 0; j < 16; j++) {
                        if (i != j) {
                            if (!kaputt[j][vilkenMatch]) {
                                somethingFixed = true;
                            }
                            kaputt[j][vilkenMatch] = true;
                        }
                    }
                }
                System.out.println("i = " + i);
                System.out.println("antalMatch = " + antalMatch);
                System.out.println();
            }
        } while (somethingFixed);

        Arrays.fill(reg, 0);
        for (; apa < strs.size(); apa++) {
            List<Integer> op = Util.findAllInts(strs.get(apa));
            if (op.isEmpty()) {
                continue;
            }
            for (int i = 0; i < 16; i++) {
                if (!kaputt[op.get(0)][i]) {
                    instructions.get(i).accept(op);
                }
            }
        }
        return String.valueOf(reg[0]);
    }

}