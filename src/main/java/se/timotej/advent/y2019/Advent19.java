package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.List;

public class Advent19 {

    public static void main(String[] args) throws IOException {
        long start = System.nanoTime();
        long svar = new Advent19().calc(Online.get());
        long duration = (System.nanoTime() - start) / 1000000;
        System.out.println("duration = " + duration);
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        Program prog = new Program(strs.get(0));
        long svar = 0;
        prog.run();
        for (int y = 0; y < 50; y++) {
            for (int x = 0; x < 50; x++) {
                prog.input.add((long) x);
                prog.input.add((long) y);
                prog.reset();
                prog.run();
                Long ut = prog.output.remove();
                if (ut == 1L) {
                    svar++;
                }
            }
        }
        return svar;
    }
}