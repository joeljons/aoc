package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Advent9 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent9().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        Program p = new Program(strs.get(0));
        InputOutput inputOutput = new InputOutput();
        inputOutput.write0(1L);
        p.run(inputOutput::read0, inputOutput::write1);
        long svar = inputOutput.read1();
        return svar;
    }

    private class InputOutput {
        Queue<Long> q0 = new ArrayDeque<>();
        Queue<Long> q1 = new ArrayDeque<>();

        Long read0() {
            return q0.remove();
        }

        void write0(Long value) {
            System.out.println("InputOutput.write0 " + value);
            q0.add(value);
        }

        Long read1() {
            return q1.remove();
        }

        void write1(Long value) {
            System.out.println("InputOutput.write1 " + value);
            q1.add(value);
        }

        public void flipflop() {
            q0 = q1;
            q1 = new ArrayDeque<>();
        }
    }

    private class Program {
        long[] initial;
        long[] g;
        long base = 0;

        public Program(String str) {
            initial = Util.findAllLongs(str).stream().mapToLong(a -> a).toArray();
            reset();
        }

        public void reset() {
            g = Arrays.copyOf(initial, initial.length + 1024 * 1024 * 100);
        }

        public void run(Supplier<Long> input, Consumer<Long> output) {
            int pos = 0;
            int lastOutput = 0;
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
                        g[getArgPos(instruction, pos, 1)] = input.get();
                        pos += 2;
                        break;
                    case 4:  // Write
                        output.accept(getArg(instruction, pos, 1));
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
