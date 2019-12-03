package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Advent19 {

    public static void main(String[] args) throws IOException {
        String svar = new Advent19().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int reg[] = new int[6];

    private String calc(List<String> strs) {
        Map<String, Consumer<List<Integer>>> instructions = new HashMap<>();
        instructions.put("addr", in -> { // addr 0
            reg[in.get(3)] = reg[in.get(1)] + reg[in.get(2)];
        });

        instructions.put("addi", in -> { // addi 1
            reg[in.get(3)] = reg[in.get(1)] + in.get(2);
        });


        instructions.put("mulr", in -> { // mulr 2
            reg[in.get(3)] = reg[in.get(1)] * reg[in.get(2)];
        });
        instructions.put("muli", in -> { // muli 3
            reg[in.get(3)] = reg[in.get(1)] * in.get(2);
        });


        instructions.put("banr", in -> { // banr 4
            reg[in.get(3)] = reg[in.get(1)] & reg[in.get(2)];
        });
        instructions.put("bani", in -> { // bani 5
            reg[in.get(3)] = reg[in.get(1)] & in.get(2);
        });


        instructions.put("borr", in -> { // borr 6
            reg[in.get(3)] = reg[in.get(1)] | reg[in.get(2)];
        });
        instructions.put("bori", in -> { // bori 7
            reg[in.get(3)] = reg[in.get(1)] | in.get(2);
        });


        instructions.put("setr", in -> { // setr 8
            reg[in.get(3)] = reg[in.get(1)];
        });
        instructions.put("seti", in -> { // seti 9
            reg[in.get(3)] = in.get(1);
        });


        instructions.put("gtir", in -> { // gtir 10
            reg[in.get(3)] = in.get(1) > reg[in.get(2)] ? 1 : 0;
        });
        instructions.put("gtri", in -> { // gtri 11
            reg[in.get(3)] = reg[in.get(1)] > in.get(2) ? 1 : 0;
        });
        instructions.put("gtrr", in -> { // gtrr 12
            reg[in.get(3)] = reg[in.get(1)] > reg[in.get(2)] ? 1 : 0;
        });


        instructions.put("eqir", in -> { // eqir 13
            reg[in.get(3)] = in.get(1) == reg[in.get(2)] ? 1 : 0;
        });
        instructions.put("eqri", in -> { // eqri 14
            reg[in.get(3)] = reg[in.get(1)] == in.get(2) ? 1 : 0;
        });
        instructions.put("eqrr", in -> { // eqrr 15
            reg[in.get(3)] = reg[in.get(1)] == reg[in.get(2)] ? 1 : 0;
        });

        int ip = 2;

        List<Runnable> prog = new ArrayList<>();

        for (String str : strs.subList(1, strs.size())) {
            String[] split = str.split(" ");
            List<Integer> op = Util.findAllInts(str);
            op.add(0, -1);
            prog.add(() -> {
                instructions.get(split[0]).accept(op);
            });
        }


        Arrays.fill(reg, 0);
        while(reg[ip]>=0 && reg[ip] < prog.size()){
            prog.get(reg[ip]).run();
            //System.out.println(Arrays.toString(reg));
            reg[ip]++;
        }
        return String.valueOf(reg[0]);
    }

}