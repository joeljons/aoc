package se.timotej.advent.y2016;

import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Advent12b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent12b().calc(Online.get(12));
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int reg[] = new int[4];
    int pos;

    private int calc(List<String> strs) {
        List<Runnable> prog = new ArrayList<>();
        reg[2] = 1;
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
            } else {
                throw new RuntimeException(str);
            }
        }
        while (pos < prog.size()) {
//            System.out.println("pos = " + pos);
//            System.out.println("reg = " + Arrays.toString(reg));
            prog.get(pos).run();
            pos++;
        }
        return reg[0];
    }

    private int getValue(String str) {
        return NumberUtils.isCreatable(str) ? Integer.parseInt(str) : reg[str.charAt(0) - 'a'];
    }

}