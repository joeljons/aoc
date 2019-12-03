package se.timotej.advent.y2016;

import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Advent25 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent25().calc(Online.get());
        System.out.println("svar = " + svar);
        //Online.submit(svar);
    }

    int reg[] = new int[4];
    int pos;
    boolean okOutput;
    int outputCount;

    private int calc(List<String> strs) {
        List<Runnable> prog = new ArrayList<>();
        for (String str : strs) {
            String[] line = str.split(" ");
            if (line[0].equals("cpy")) {
                prog.add(() -> {
                    int val = getValue(line[1]);
                    reg[line[2].charAt(0) - 'a'] = val;
                });
            } else if (line[0].equals("inc")) {
                prog.add(() -> reg[line[1].charAt(0) - 'a']++);
            } else if (line[0].equals("dec")) {
                prog.add(() -> reg[line[1].charAt(0) - 'a']--);
            } else if (line[0].equals("jnz")) {
                prog.add(() -> {
                    if (getValue(line[1]) != 0) {
                        pos += getValue(line[2]) - 1;
                    }
                });
            } else if (line[0].equals("out")) {
                prog.add(() -> {
                    if (getValue(line[1]) != outputCount++ % 2) {
                        okOutput = false;
                    }
                });
            } else {
                throw new RuntimeException(str);
            }
        }
        int i = 0;
        while (!validate(prog, i)) {
            i++;
        }
        return i;
    }

    private boolean validate(List<Runnable> prog, int startRegA) {
        reg[0] = startRegA;
        reg[1] = reg[2] = reg[3] = pos = 0;
        outputCount = 0;
        okOutput = true;
        while (pos < prog.size()) {
            prog.get(pos).run();
            pos++;
            if (!okOutput) {
                return false;
            } else if (outputCount == 1024) {
                return true;
            }
        }
        return false;
    }

    private int getValue(String str) {
        return NumberUtils.isCreatable(str) ? Integer.parseInt(str) : reg[str.charAt(0) - 'a'];
    }

}