package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent21 {

    private static final int LIMIT = 100_000_000;

    public static void main(String[] args) throws IOException {
        String svar = new Advent21().calc(Online.get());
        System.out.println("svar = " + svar);
//        Online.submit(19,2,svar);
    }

    int reg[] = new int[6];
    int lastReg3=0;

    private String calc(List<String> strs) {
        int ip = 1;
        Set<Integer> seenReg3 = new HashSet<>();
        List<Runnable> prog = new ArrayList<>();
        prog.add(() -> reg[3] = 123);
        prog.add(() -> reg[3] = reg[3] & 456);
        prog.add(() -> reg[3] = reg[3] == 72 ? 1 : 0);
        prog.add(() -> reg[1] = reg[3] + reg[1]);
        prog.add(() -> reg[1] = 0);
        prog.add(() -> reg[3] = 0);
        prog.add(() -> reg[2] = reg[3] | 65536);
        prog.add(() -> reg[3] = 1505483);
        prog.add(() -> reg[4] = reg[2] & 255);
        prog.add(() -> reg[3] = reg[3] + reg[4]);
        prog.add(() -> reg[3] = reg[3] & 16777215);
        prog.add(() -> reg[3] = reg[3] * 65899);
        prog.add(() -> reg[3] = reg[3] & 16777215);
        prog.add(() -> reg[4] = 256 > reg[2] ? 1 : 0);
        prog.add(() -> reg[1] = reg[4] + reg[1]);
        prog.add(() -> reg[1] = reg[1] + 1);
        prog.add(() -> reg[1] = 27);
        prog.add(() -> reg[4] = 0);
        prog.add(() -> reg[5] = reg[4] + 1);
        prog.add(() -> reg[5] = reg[5] * 256);
        prog.add(() -> reg[5] = reg[5] > reg[2] ? 1 : 0);
        prog.add(() -> reg[1] = reg[5] + reg[1]);
        prog.add(() -> reg[1] = reg[1] + 1);
        prog.add(() -> reg[1] = 25);
        prog.add(() -> reg[4] = reg[4] + 1);
        prog.add(() -> reg[1] = 17);
        prog.add(() -> reg[2] = reg[4]);
        prog.add(() -> reg[1] = 7);
        prog.add(() -> {
            if(lastReg3 == 0){
                System.out.println("reg[3] = " + reg[3]);
            }
            if (!seenReg3.add(reg[3])) {
                System.out.println("lastReg3 = " + lastReg3);
                System.exit(0);
            }
            lastReg3 = reg[3];
            reg[4] = reg[3] == reg[0] ? 1 : 0;
        });
        prog.add(() -> reg[1] = reg[4] + reg[1]);
        prog.add(() -> reg[1] = 5);


        int steps = 0;
        Arrays.fill(reg, 0);
        while (reg[ip] >= 0 && reg[ip] < prog.size()) {
            prog.get(reg[ip]).run();
            reg[ip]++;
            steps++;
        }
        return "";
    }
}

