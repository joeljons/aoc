package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Advent16 {

    public static void main(String[] args) throws IOException {
        String svar = new Advent16().calc(Online.get());
        System.out.println("svar = " + svar);
//        Online.submit(svar);
    }

    int reg[] = new int[4];

    private String calc(List<String> strs) {
        List<Consumer<List<Integer>>> instructions = new ArrayList<>();
        instructions.add(in -> { // addr
            reg[in.get(3)] = reg[in.get(1)] + reg[in.get(2)];
        });
        instructions.add(in -> { // addi
            reg[in.get(3)] = reg[in.get(1)] + in.get(2);
        });


        instructions.add(in -> { // mulr
            reg[in.get(3)] = reg[in.get(1)] * reg[in.get(2)];
        });
        instructions.add(in -> { // muli
            reg[in.get(3)] = reg[in.get(1)] * in.get(2);
        });


        instructions.add(in -> { // banr
            reg[in.get(3)] = reg[in.get(1)] & reg[in.get(2)];
        });
        instructions.add(in -> { // bani
            reg[in.get(3)] = reg[in.get(1)] & in.get(2);
        });


        instructions.add(in -> { // borr
            reg[in.get(3)] = reg[in.get(1)] | reg[in.get(2)];
        });
        instructions.add(in -> { // bori
            reg[in.get(3)] = reg[in.get(1)] | in.get(2);
        });


        instructions.add(in -> { // setr
            reg[in.get(3)] = reg[in.get(1)];
        });
        instructions.add(in -> { // seti
            reg[in.get(3)] = in.get(1);
        });


        instructions.add(in -> { // gtir
            reg[in.get(3)] = in.get(1) > reg[in.get(2)] ? 1 : 0;
        });
        instructions.add(in -> { // gtri
            reg[in.get(3)] = reg[in.get(1)] > in.get(2) ? 1 : 0;
        });
        instructions.add(in -> { // gtrr
            reg[in.get(3)] = reg[in.get(1)] > reg[in.get(2)] ? 1 : 0;
        });


        instructions.add(in -> { // eqir
            reg[in.get(3)] = in.get(1) == reg[in.get(2)] ? 1 : 0;
        });
        instructions.add(in -> { // eqri
            reg[in.get(3)] = reg[in.get(1)] == in.get(2) ? 1 : 0;
        });
        instructions.add(in -> { // eqrr
            reg[in.get(3)] = reg[in.get(1)] == reg[in.get(2)] ? 1 : 0;
        });



        int svar = 0;
        for (int apa = 0; apa < strs.size(); apa += 4) {
            List<Integer> before = Util.findAllInts(strs.get(apa));
            if (before.isEmpty()) {
                break;
            }
            List<Integer> op = Util.findAllInts(strs.get(apa + 1));
            List<Integer> after = Util.findAllInts(strs.get(apa + 2));

            int antalMatch = 0;
            for (Consumer<List<Integer>> instruction : instructions) {
                for (int i = 0; i < 4; i++) {
                    reg[i] = before.get(i);
                }
                instruction.accept(op);
                int match = 1;
                for (int i = 0; i < 4; i++) {
                    if (reg[i] != after.get(i)) {
                        match = 0;
                    }
                }
                antalMatch += match;
            }
            if (antalMatch >= 3) {
                svar++;
            }
        }
        return String.valueOf(svar);
    }

}