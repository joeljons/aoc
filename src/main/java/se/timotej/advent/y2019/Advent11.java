package se.timotej.advent.y2019;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Advent11 {

    public static void main(String[] args) throws IOException, InterruptedException {
        long start = System.nanoTime();
        long svar = new Advent11().calc(Online.get());
        long duration = (System.nanoTime() - start) / 1000000;
        System.out.println("duration = " + duration);
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dy = {-1, 0, 1, 0};
    int[] dx = {0, 1, 0, -1};

    private long calc(List<String> strs) throws InterruptedException {
        Program prog = new Program(strs.get(0));

        Map<Pair<Integer, Integer>, Integer> g = new HashMap<>();
        int x = 0;
        int y = 0;
        int dir = 0;

        while (!prog.done) {
            prog.input.add((long) g.getOrDefault(Pair.of(x, y), 0));
            prog.run();
            if (prog.output.size() == 2) {
                Long paintColor = prog.output.remove();
                g.put(Pair.of(x, y), paintColor.intValue());
                Long ddir = prog.output.remove();
                if (ddir == 0) {
                    dir = (dir + 3) % 4;
                } else if (ddir == 1) {
                    dir = (dir + 1) % 4;
                } else {
                    throw new RuntimeException("Invalid ddir " + ddir);
                }
                x += dx[dir];
                y += dy[dir];

            } else {
                throw new RuntimeException("Expected 2 outputs, got " + prog.output.size());
            }
        }

        return g.size();
    }

    private class Program {
        long[] initial;
        long[] g = new long[1024 * 1024];
        long base = 0;
        volatile boolean done = false;
        Queue<Long> input = new ArrayDeque<>();
        Queue<Long> output = new ArrayDeque<>();
        int pos;

        public Program(String str) {
            initial = Util.findAllLongs(str).stream().mapToLong(a -> a).toArray();
            reset();
            pos = 0;
        }

        public void reset() {
            Arrays.fill(g, 0);
            System.arraycopy(initial, 0, g, 0, initial.length);
        }

        public void run() {
            while (g[pos] != 99) {
                long instruction = g[pos];
                int opcode = (int) (instruction % 100);
                switch (opcode) {
                    case 1:  // Add
                        g[getArgPos(instruction, pos, 3)] = getArg(instruction, pos, 1) + getArg(instruction, pos, 2);
                        pos += 4;
                        break;
                    case 2:  // Multiply
                        g[getArgPos(instruction, pos, 3)] = (getArg(instruction, pos, 1)) * getArg(instruction, pos, 2);
                        pos += 4;
                        break;
                    case 3:  // Read
                        if (input.isEmpty()) {
                            return;
                        }
                        g[getArgPos(instruction, pos, 1)] = input.remove();
                        pos += 2;
                        break;
                    case 4:  // Write
                        output.add(getArg(instruction, pos, 1));
                        pos += 2;
                        break;
                    case 5:  // Jump-if-true
                        if (getArg(instruction, pos, 1) != 0) {
                            pos = (int) getArg(instruction, pos, 2);
                        } else {
                            pos += 3;
                        }
                        break;
                    case 6:  // Jump-if-false
                        if (getArg(instruction, pos, 1) == 0) {
                            pos = (int) getArg(instruction, pos, 2);
                        } else {
                            pos += 3;
                        }
                        break;
                    case 7:  // Less than
                        if (getArg(instruction, pos, 1) < getArg(instruction, pos, 2)) {
                            g[getArgPos(instruction, pos, 3)] = 1;
                        } else {
                            g[getArgPos(instruction, pos, 3)] = 0;
                        }
                        pos += 4;
                        break;
                    case 8:  // Equals
                        if (getArg(instruction, pos, 1) == getArg(instruction, pos, 2)) {
                            g[getArgPos(instruction, pos, 3)] = 1;
                        } else {
                            g[getArgPos(instruction, pos, 3)] = 0;
                        }
                        pos += 4;
                        break;
                    case 9:  // Base
                        base += getArg(instruction, pos, 1);
                        pos += 2;
                        break;
                    default:
                        throw new RuntimeException(String.valueOf(instruction));
                }
            }
            done = true;
        }

        private long getArg(long instruction, int pos, int argNum) {
            return g[getArgPos(instruction, pos, argNum)];
        }

        private int getArgPos(long instruction, int pos, int argNum) {
            int mode = getMode(instruction, argNum);
            if (mode == 0) {
                return (int) (g[pos + argNum]);
            } else if (mode == 1) {
                return pos + argNum;
            } else if (mode == 2) {
                return (int) (g[pos + argNum] + base);
            } else {
                throw new RuntimeException("Mode " + mode);
            }
        }

        private int getMode(long instruction, int argNum) {
            long divide = argNum == 1 ? 100 : (argNum == 2 ? 1000 : (argNum == 3 ? 10000 : 0));
            return (int) ((instruction / divide) % 10);
        }
    }

}
