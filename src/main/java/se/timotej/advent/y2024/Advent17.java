package se.timotej.advent.y2024;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Advent17 {

    public static void main(String[] args) throws IOException {
        String svar = new Advent17().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private String calc(List<String> strs) {

        long a = Util.findAllLongs(strs.get(0)).getFirst();
        long b = Util.findAllLongs(strs.get(1)).getFirst();
        long c = Util.findAllLongs(strs.get(2)).getFirst();
        List<Integer> prog = Util.findAllInts(strs.get(4));
        List<Integer> output = new ArrayList<>();

        int pos = 0;
        while (pos >= 0 && pos < prog.size() - 1) {
            int opcode = prog.get(pos);
            int operand = prog.get(pos + 1);
            switch (opcode) {
                // adv
                case 0 -> a >>= combo(operand, a, b, c);

                //bxl
                case 1 -> b ^= operand;

                //bst
                case 2 -> b = combo(operand, a, b, c) % 8;

                //jnz
                case 3 -> {
                    if (a != 0) {
                        pos = operand - 2;
                    }
                }

                //bxc
                case 4 -> b ^= c;

                //out
                case 5 -> output.add((int)(combo(operand, a, b, c) % 8));

                //bdv
                case 6 -> b = a>>combo(operand, a, b, c);

                //cdv
                case 7 -> c = a>>combo(operand, a, b, c);

                default -> throw new RuntimeException("Unknown opcode: " + opcode);
            }
            pos += 2;
        }

        return StringUtils.join(output, ",");
    }

    private long combo(int operand, long a, long b, long c) {
        return switch (operand) {
            case 0, 1, 2, 3 -> operand;
            case 4 -> a;
            case 5 -> b;
            case 6 -> c;
            default -> throw new RuntimeException("Unknown combo operand: " + operand);
        };
    }
}
