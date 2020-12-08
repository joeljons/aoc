package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent8 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent8().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int acc = 0;
        Set<Integer> ranBefore = new HashSet<>();
        int pos = 0;
        while (true) {
            if (!ranBefore.add(pos)) {
                return acc;
            }
            String str = strs.get(pos);
            String[] split = str.split(" ");
            String instruction = split[0];
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
}
