package se.timotej.advent.y2019;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent17b {

    public static void main(String[] args) throws IOException {
        long start = System.nanoTime();
        long svar = new Advent17b().calc(Online.get());
        long duration = (System.nanoTime() - start) / 1000000;
        System.out.println("duration = " + duration);
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};

    private long calc(List<String> strs) {
        Program prog = new Program(strs.get(0));
        long svar = 0;
        prog.run();
        int y = 0;
        int x = 0;
        Map<Pair<Integer, Integer>, Character> g = new HashMap<>();
        int curX = 0;
        int curY = 0;
        int curDir = 0;
        while (!prog.output.isEmpty()) {
            char next = (char) prog.output.remove().intValue();
            System.out.print(next);
            if (next == '\n') {
                y++;
                x = 0;
                continue;
            }
            g.put(Pair.of(x, y), next);
            if (next == '^') {
                curX = x;
                curY = y;
            }
            x++;
        }

        StringBuilder path = new StringBuilder();
        int count = 0;
        x = curX;
        y = curY;
        int dir = curDir;
        while (true) {
            int nyx = x + dx[dir];
            int nyy = y + dy[dir];
            Pair<Integer, Integer> ny = Pair.of(nyx, nyy);
            if (g.containsKey(ny) && g.get(ny) == '#') {
                count++;
                x = nyx;
                y = nyy;
            } else {
                if (count > 0) {
                    path.append(count).append(',');
                    count = 0;
                }
                int nydir = (dir + 3) % 4;
                nyx = x + dx[nydir];
                nyy = y + dy[nydir];
                ny = Pair.of(nyx, nyy);
                if (g.containsKey(ny) && g.get(ny) == '#') {
                    path.append('L').append(',');
                    dir = nydir;
                } else {
                    nydir = (dir + 1) % 4;
                    nyx = x + dx[nydir];
                    nyy = y + dy[nydir];
                    ny = Pair.of(nyx, nyy);
                    if (g.containsKey(ny) && g.get(ny) == '#') {
                        path.append('R').append(',');
                        dir = nydir;
                    } else {
                        break;
                    }
                }
            }
        }

        String input = path.substring(0, path.length() - 1);
        System.out.println("input = " + input);

        prog.reset();
        prog.g[0] = 2;
        prog.write("A,A,B,C,B,A,C,B,C,A\n");
        prog.write("L,6,R,12,L,6,L,8,L,8\n");
        prog.write("L,6,R,12,R,8,L,8\n");
        prog.write("L,4,L,4,L,6\n");
        prog.write("n\n");
        prog.run();
        while (!prog.output.isEmpty()) {
            Long output = prog.output.remove();
            svar = output;
            System.out.print((char) output.intValue());
        }
        return svar;
    }
}
