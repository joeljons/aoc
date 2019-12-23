package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.List;

public class Advent23b {

    public static void main(String[] args) throws IOException {
        long start = System.nanoTime();
        long svar = new Advent23b().calc(Online.get());
        long duration = (System.nanoTime() - start) / 1000000;
        System.out.println("duration = " + duration);
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        Program[] prog = new Program[50];
        for (int i = 0; i < 50; i++) {
            prog[i] = new Program(strs.get(0));
            prog[i].input.add((long) i);
            prog[i].run();
        }
        long lastNATX = -42;
        long lastNATY = -42;
        long lastlastNATY = -43;
        while (true) {
            boolean idle = true;
            for (int i = 0; i < 50; i++) {
                if (prog[i].input.isEmpty()) {
                    prog[i].input.add(-1L);
                }
                prog[i].run();
                while (!prog[i].output.isEmpty()) {
                    idle = false;
                    int destination = prog[i].output.remove().intValue();
                    Long x = prog[i].output.remove();
                    Long y = prog[i].output.remove();
                    if (destination == 255) {
                        lastNATY = y;
                        lastNATX = x;
                    } else {
                        prog[destination].input.add(x);
                        prog[destination].input.add(y);
                    }
                }
            }
            if (idle) {
                if (lastNATY == lastlastNATY) {
                    return lastNATY;
                }
                lastlastNATY = lastNATY;
                prog[0].input.add(lastNATX);
                prog[0].input.add(lastNATY);
            }
        }
    }
}
