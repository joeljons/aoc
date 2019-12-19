package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.List;

public class Advent19b {

    public static void main(String[] args) throws IOException {
        long start = System.nanoTime();
        long svar = new Advent19b().calc(Online.get());
        long duration = (System.nanoTime() - start) / 1000000;
        System.out.println("duration = " + duration);
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        Program prog = new Program(strs.get(0), 512);
        int minx = 0;
        for (int y = 0; y < 10000; y++) {
            int startx = 0;
            for (int x = minx; x < 10000; x++) {
                if (isBeam(prog, y, x)) {
                    if (startx == 0) {
                        startx = x;
                        minx = x;
                    }
                } else if (startx != 0) {
                    if (y > 100 && isFunkar(prog, y, startx)) {
                        return startx * 10000 + y - 99;
                    }
                    break;
                }

            }
        }
        return 0;
    }

    private boolean isFunkar(Program prog, int y, int x) {
        for (int d = 99; d >= 0; d--) {
            if (!isBeam(prog, y - d, x + 99)) {
                return false;
            }
            if (!isBeam(prog, y - 99, x + d)) {
                return false;
            }
            if (!isBeam(prog, y - d, x)) {
                return false;
            }
            if (!isBeam(prog, y, x + d)) {
                return false;
            }
        }
        return true;
    }

    private boolean isBeam(Program prog, long y, long x) {
        prog.input.add(x);
        prog.input.add(y);
        prog.reset();
        prog.run();
        Long ut = prog.output.remove();
        return ut == 1L;
    }
}
