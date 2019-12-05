package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.List;

public class Advent5b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent5b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        Integer[] g = Util.findAllInts(strs.get(0)).toArray(new Integer[0]);
        int pos = 0;
        int lastOutput = 0;
        while (g[pos] != 99) {
            int instruction = g[pos];
            if (instruction % 100 == 1) {
                g[g[pos + 3]] = ((instruction / 100) % 10 == 1 ? g[pos + 1] : g[g[pos + 1]]) +
                        ((instruction / 1000) % 10 == 1 ? g[pos + 2] : g[g[pos + 2]]);
                pos += 4;
            } else if (instruction % 100 == 2) {
                g[g[pos + 3]] = ((instruction / 100) % 10 == 1 ? g[pos + 1] : g[g[pos + 1]]) *
                        ((instruction / 1000) % 10 == 1 ? g[pos + 2] : g[g[pos + 2]]);
                pos += 4;
            } else if (instruction % 100 == 3) {
                g[g[pos + 1]] = 5;
                pos += 2;
            } else if (instruction % 100 == 4) {
                lastOutput = g[g[pos + 1]];
                System.out.println("lastOutput = " + lastOutput);
                pos += 2;
            } else if (instruction % 100 == 5) {
                if (((instruction / 100) % 10 == 1 ? g[pos + 1] : g[g[pos + 1]]) != 0) {
                    pos = ((instruction / 1000) % 10 == 1 ? g[pos + 2] : g[g[pos + 2]]);
                } else {
                    pos += 3;
                }
            } else if (instruction % 100 == 6) {
                if (((instruction / 100) % 10 == 1 ? g[pos + 1] : g[g[pos + 1]]) == 0) {
                    pos = ((instruction / 1000) % 10 == 1 ? g[pos + 2] : g[g[pos + 2]]);
                } else {
                    pos += 3;
                }
            } else if (instruction % 100 == 7) {
                if (((instruction / 100) % 10 == 1 ? g[pos + 1] : g[g[pos + 1]]) < ((instruction / 1000) % 10 == 1 ? g[pos + 2] : g[g[pos + 2]])) {
                    g[g[pos + 3]] = 1;
                } else {
                    g[g[pos + 3]] = 0;
                }
                pos += 4;
            } else if (instruction % 100 == 8) {
                if (((instruction / 100) % 10 == 1 ? g[pos + 1] : g[g[pos + 1]]).equals((instruction / 1000) % 10 == 1 ? g[pos + 2] : g[g[pos + 2]])) {
                    g[g[pos + 3]] = 1;
                } else {
                    g[g[pos + 3]] = 0;
                }
                pos += 4;
            } else {
                throw new RuntimeException(String.valueOf(instruction));
            }
        }
        return lastOutput;
    }


}
