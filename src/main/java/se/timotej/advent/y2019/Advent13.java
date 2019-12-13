package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.List;

public class Advent13 {

    public static void main(String[] args) throws IOException, InterruptedException {
        long start = System.nanoTime();
        long svar = new Advent13().calc(Online.get());
        long duration = (System.nanoTime() - start) / 1000000;
        System.out.println("duration = " + duration);
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        Program prog = new Program(strs.get(0));
        prog.run();
        int svar = 0;
        while (!prog.output.isEmpty()) {
            Long x = prog.output.remove();
            Long y = prog.output.remove();
            Long tile = prog.output.remove();
            if (tile.intValue() == 2) {
                svar++;
            }
        }
        return svar;
    }

}
