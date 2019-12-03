package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Advent19b_apa {

    public static void main(String[] args) throws IOException {
        String svar = new Advent19b_apa().calc(Online.get(19));
        System.out.println("svar = " + svar);
//        Online.submit(19,2,svar);
    }

    int reg[] = new int[6];

    private String calc(List<String> strs) {
        Map<String, Consumer<int[]>> instructions = new HashMap<>();
        instructions.put("addr", in -> { // addr 0
            reg[in[2]] = reg[in[0]] + reg[in[1]];
        });

        instructions.put("addi", in -> { // addi 1
            reg[in[2]] = reg[in[0]] + in[1];
        });


        instructions.put("mulr", in -> { // mulr 2
            reg[in[2]] = reg[in[0]] * reg[in[1]];
        });
        instructions.put("muli", in -> { // muli 3
            reg[in[2]] = reg[in[0]] * in[1];
        });


        instructions.put("banr", in -> { // banr 4
            reg[in[2]] = reg[in[0]] & reg[in[1]];
        });
        instructions.put("bani", in -> { // bani 5
            reg[in[2]] = reg[in[0]] & in[1];
        });


        instructions.put("borr", in -> { // borr 6
            reg[in[2]] = reg[in[0]] | reg[in[1]];
        });
        instructions.put("bori", in -> { // bori 7
            reg[in[2]] = reg[in[0]] | in[1];
        });


        instructions.put("setr", in -> { // setr 8
            reg[in[2]] = reg[in[0]];
        });
        instructions.put("seti", in -> { // seti 9
            reg[in[2]] = in[0];
        });


        instructions.put("gtir", in -> { // gtir 10
            reg[in[2]] = in[0] > reg[in[1]] ? 1 : 0;
        });
        instructions.put("gtri", in -> { // gtri 11
            reg[in[2]] = reg[in[0]] > in[1] ? 1 : 0;
        });
        instructions.put("gtrr", in -> { // gtrr 12
            reg[in[2]] = reg[in[0]] > reg[in[1]] ? 1 : 0;
        });


        instructions.put("eqir", in -> { // eqir 13
            reg[in[2]] = in[0] == reg[in[1]] ? 1 : 0;
        });
        instructions.put("eqri", in -> { // eqri 14
            reg[in[2]] = reg[in[0]] == in[1] ? 1 : 0;
        });
        instructions.put("eqrr", in -> { // eqrr 15
            reg[in[2]] = reg[in[0]] == reg[in[1]] ? 1 : 0;
        });

        int ip = 2;

        List<Runnable> prog = new ArrayList<>();

        for (String str : strs.subList(1, strs.size())) {
            String[] split = str.split(" ");
            List<Integer> op = Util.findAllInts(str);
            int[] ops = new int[3];
            ops[0] = op.get(0);
            ops[1] = op.get(1);
            ops[2] = op.get(2);

            op.add(0, -1);
            Consumer<int[]> instruction = instructions.get(split[0]);
            prog.add(() -> {
                instruction.accept(ops);
            });
        }


        Arrays.fill(reg, 0);
        reg[0] = 1;
        int steps = 0;
        while (reg[ip] >= 0 && reg[ip] < prog.size()) {
            prog.get(reg[ip]).run();
            reg[ip]++;
            steps++;
            if (steps % 1000000 == 0) {
                System.out.println(Arrays.toString(reg));
            }

        }
        System.out.println("steps = " + steps);
        return String.valueOf(reg[0]);
    }

}