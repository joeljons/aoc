package se.timotej.advent.y2019;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent17 {

    public static void main(String[] args) throws IOException {
        long start = System.nanoTime();
        long svar = new Advent17().calc(Online.get());
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
        while (!prog.output.isEmpty()) {
            char next = (char) prog.output.remove().intValue();
            System.out.print(next);
            if (next == '\n') {
                y++;
                x = 0;
                continue;
            }
            g.put(Pair.of(x, y), next);
            x++;
        }
        for (Pair<Integer, Integer> integerIntegerPair : g.keySet()) {
            x = integerIntegerPair.getKey();
            y = integerIntegerPair.getValue();
            if (g.get(integerIntegerPair) == '#') {
                boolean yes = true;
                for (int dir = 0; dir < 4; dir++) {
                    int nyx = x + dx[dir];
                    int nyy = y + dy[dir];
                    Pair<Integer, Integer> ny = Pair.of(nyx, nyy);
                    if (!g.containsKey(ny) || (g.get(ny) != '#' && g.get(ny) != 'O')) {
                        yes = false;
                        break;
                    }
                }
                if (yes) {
                    g.put(integerIntegerPair, 'O');
                    svar += x * y;
                }
            }
        }
        System.out.println();
        System.out.println();
        for (y = 0; y < 47; y++) {
            for (x = 0; x < 45; x++){
                System.out.print(g.get(Pair.of(x, y)));
            }
            System.out.println();
        }
        return svar;
    }
}
