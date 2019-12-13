package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.List;

public class Advent13b {

    public static void main(String[] args) throws IOException, InterruptedException {
        long start = System.nanoTime();
        long svar = new Advent13b().calc(Online.get());
        long duration = (System.nanoTime() - start) / 1000000;
        System.out.println("duration = " + duration);
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int paddleX;
    int ballX;
    long svar;

    private long calc(List<String> strs) {
        Program prog = new Program(strs.get(0));
        prog.g[0] = 2;
        prog.run();
        readInput(prog);
        while (!prog.done) {
            prog.input.add((long)Integer.compare(ballX, paddleX));
            prog.run();
            readInput(prog);
        }
        return svar;
    }

    private void readInput(Program prog) {
        while (!prog.output.isEmpty()) {
            Long x = prog.output.remove();
            Long y = prog.output.remove();
            Long tile = prog.output.remove();
            if (tile == 3L) {
                paddleX = x.intValue();
            }
            if (tile == 4L) {
                ballX = x.intValue();
            }
            if (x == -1L && y == 0L) {
                svar = tile;
            }
        }
    }

}
