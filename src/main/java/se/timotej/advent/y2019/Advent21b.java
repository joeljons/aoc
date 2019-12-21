package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Advent21b {

    public static void main(String[] args) throws IOException {
        long start = System.nanoTime();
        long svar = new Advent21b().calc(Online.get());
        long duration = (System.nanoTime() - start) / 1000000;
        System.out.println("duration = " + duration);
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        Program prog = new Program(strs.get(0), 3000);
        Random r = new Random();
        for (int tries = 0; ; tries++) {
            if (tries % 10000 == 0) {
                System.out.println("tries = " + tries);
            }
            int instructions = r.nextInt(7);
            String[][] instrs = new String[][]{{"AND", "OR", "NOT"},
                    {"J", "T", "A", "B", "C", "D", "E", "F", "G", "H", "I"},
                    {"J", "T"}};
            StringBuilder p = new StringBuilder();
            p.append("OR A J\n" +
                    "AND B J\n" +
                    "AND C J\n");
            for (int i = 0; i < instructions; i++) {
                for (int j = 0; j < 3; j++) {
                    if (j != 0) {
                        p.append(" ");
                    }
                    p.append(instrs[j][r.nextInt(instrs[j].length)]);
                }
                p.append("\n");
            }
            p.append("NOT J J\n" +
                    "AND D J\n");

            p.append("RUN\n");
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