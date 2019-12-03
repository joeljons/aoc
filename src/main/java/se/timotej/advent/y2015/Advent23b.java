package se.timotej.advent.y2015;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Advent23b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent23b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int reg[] = new int[2];
    int pos;

    private int calc(List<String> strs) {
        List<Runnable> prog = new ArrayList<>();
        strs.add(0, "inc a");
        for (String str : strs) {
            String[] line = str.split("[, ]+");
            if (line[0].equals("hlf")) {
                prog.add(() -> reg[line[1].charAt(0) - 'a'] /= 2);
            } else if (line[0].equals("tpl")) {
                prog.add(() -> reg[line[1].charAt(0) - 'a'] *= 3);
            } else if (line[0].equals("inc")) {
                prog.add(() -> reg[line[1].charAt(0) - 'a']++);
            } else if (line[0].equals("jmp")) {
                prog.add(() -> pos += getValue(line[1]) - 1);
            } else if (line[0].equals("jie")) {
                prog.add(() -> {
                    if (reg[line[1].charAt(0) - 'a'] % 2 == 0) {
                        pos += getValue(line[2]) - 1;
                    }
                });
            } else if (line[0].equals("jio")) {
                prog.add(() -> {
                    if (reg[line[1].charAt(0) - 'a'] == 1) {
                        pos += getValue(line[2]) - 1;
                    }
                });
            } else {
                throw new RuntimeException(str);
            }
        }
        while (pos < prog.size()) {
            System.out.println("pos = " + pos);
            System.out.println("reg = " + Arrays.toString(reg));
            prog.get(pos).run();
            pos++;
        }
        return (int) reg[1];
    }

    private int getValue(String str) {
        return Integer.parseInt(str);
    }

}