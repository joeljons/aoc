package se.timotej.advent.y2019;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

class Program {
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
        pos = 0;
        base = 0;
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

    public void write(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            input.add((long)c);
        }
    }
}
