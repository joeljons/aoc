package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent8b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent8b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        for (int byt = 0; byt < strs.size(); byt++) {
            int acc = 0;
            Set<Integer> ranBefore = new HashSet<>();
            int pos = 0;
            while (true) {
                if (!ranBefore.add(pos)) {
                    break;
                }
                if (pos == strs.size()) {
                    return acc;
                }
                String str = strs.get(pos);
                String[] split = str.split(" ");
                String instruction = split[0];
                if (pos == byt) {
                    if (instruction.equals("jmp")) {
                        instruction = "nop";
                    } else if (instruction.equals("nop")) {
                        instruction = "jmp";
                    }
                }
                int value = Integer.parseInt(split[1]);
                if (instruction.equals("acc")) {
                    acc += value;
                    pos++;
                } else if (instruction.equals("jmp")) {
                    pos += value;
                } else {
                    pos++;
                }
            }
        }
        return 0;
    }
}
