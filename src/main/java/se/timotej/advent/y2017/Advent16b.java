package se.timotej.advent.y2017;

import java.io.IOException;
import java.util.List;

public class Advent16b {
    public static void main(String[] args) throws IOException {
        new Advent16b().calc(Online.get(16));
    }

    private void calc(List<String> strs) {
        String str = strs.get(0);
        String[] split = str.split(",");
        Instruction[] instructions = new Instruction[split.length];
        for (int i = 0; i < split.length; i++) {
            String instr = split[i];
            Instruction inst = instructions[i] = new Instruction();
            inst.operation = instr.charAt(0);
            if (inst.operation == 's') {
                inst.num0 = Integer.parseInt(instr.substring(1));
            } else if (inst.operation == 'x') {
                String[] s1 = instr.substring(1).split("/");
                inst.num0 = Integer.parseInt(s1[0]);
                inst.num1 = Integer.parseInt(s1[1]);
            } else if (inst.operation == 'p') {
                inst.char0 = instr.charAt(1);
                inst.char1 = instr.charAt(3);
            } else {
                throw new RuntimeException(instr);
            }
        }
        char g[] = new char[16];
        for (int i = 0; i < 16; i++) {
            g[i] = (char) ('a' + i);
        }
        long start = System.nanoTime();
        for (int it = 0; it < 1000000000; it++) {

            for (Instruction instr : instructions) {
                if (instr.operation == 's') {
                    for (int i = 0; i < instr.num0; i++) {
                        char tmp = g[15];
                        for (int j = 15; j > 0; j--) {
                            g[j] = g[j - 1];
                        }
                        g[0] = tmp;
                    }
                } else if (instr.operation == 'x') {
                    char tmp = g[instr.num0];
                    g[instr.num0] = g[instr.num1];
                    g[instr.num1] = tmp;
                } else if (instr.operation == 'p') {
                    int p1 = -1;
                    int p2 = -1;
                    for (int i = 0; i < 16; i++) {
                        if (g[i] == instr.char0) {
                            p1 = i;
                        }
                        if (g[i] == instr.char1) {
                            p2 = i;
                        }
                    }
                    char tmp = g[p1];
                    g[p1] = g[p2];
                    g[p2] = tmp;
                }
            }
            if (new String(g).equals("abcdefghijklmnop")) {
                int len = (it+1);
                while(it+len<1000000000){
                    it += len;
                }
            }
        }
        System.out.println(new String(g));
    }


    private class Instruction {
        char operation;
        int num0;
        int num1;
        char char0;
        char char1;
    }
}