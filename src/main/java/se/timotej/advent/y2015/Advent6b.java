package se.timotej.advent.y2015;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.List;

public class Advent6b {
    public static void main(String[] args) throws IOException {
        new Advent6b().calc(Online.get(6));
    }

    int[][] g = new int[1000][1000];

    private void calc(List<String> strs) {
        for (String str : strs) {
            String[] line = str.split(" ");
            boolean dest = false;
            boolean toggle = false;
            Pair<Integer, Integer> from, to;
            if (line[0].equals("turn")) {
                dest = line[1].equals("on");
                from = parsePoint(line[2]);
                to = parsePoint(line[4]);
            } else {
                from = parsePoint(line[1]);
                to = parsePoint(line[3]);
                toggle = true;
            }
            for (int x = from.getLeft(); x <= to.getLeft(); x++) {
                for (int y = from.getRight(); y <= to.getRight(); y++) {
                    if (toggle) {
                        g[y][x] += 2;
                    } else {
                        g[y][x] += dest ? 1 : -1;
                        if (g[y][x] < 0) {
                            g[y][x] = 0;
                        }
                    }
                }
            }
        }
        int count = 0;
        for (int[] bs : g) {
            for (int b : bs) {
                count += b;
            }
        }
        System.out.println("count = " + count);
        Online.submit(6, 2, count);
    }

    private Pair<Integer, Integer> parsePoint(String p) {
        String[] split = p.split(",");
        return Pair.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }
}
