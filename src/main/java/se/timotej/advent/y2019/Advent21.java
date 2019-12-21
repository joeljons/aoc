package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Advent21 {

    public static void main(String[] args) throws IOException {
        long start = System.nanoTime();
        long svar = new Advent21().calc(Online.get());
        long duration = (System.nanoTime() - start) / 1000000;
        System.out.println("duration = " + duration);
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        Program prog = new Program(strs.get(0), 3000);
        Random r = new Random();
        for (int tries = 0; ; tries++) {
            int instructions = r.nextInt(5);
            String[][] instrs = new String[][]{{"AND", "OR", "NOT"},
                    {"J", "A", "B", "C", "D"},
                    {"J"}};
            StringBuilder p = new StringBuilder();
            p.append("OR A J\n");
            for (int i = 0; i < instructions; i++) {
                for (int j = 0; j < 3; j++) {
                    if (j != 0) {
                        p.append(" ");
                    }
                    p.append(instrs[j][r.nextInt(instrs[j].length)]);
                }
                p.append("\n");
            }
            p.append("WALK\n");
            prog.reset();
            prog.run();
            while (!prog.output.isEmpty()) {
                Long output = prog.output.remove();
            }
            prog.write(p.toString());
            prog.run();
            while (!prog.output.isEmpty()) {
                Long output = prog.output.remove();
                if (output > 255) {
                    System.out.println("tries = " + tries);
                    System.out.println("p = " + p);
                    return output;
                }
            }
        }
    }
}