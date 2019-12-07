package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Advent7 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent7().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        Integer[] order = new Integer[]{0,1,2,3,4};
        Program p = new Program(strs.get(0));
        int maxLastUt = 0;
        do {
            int lastUt = 0;
            for(int i=0;i<5;i++) {
                p.reset();
                InputOutput inputOutput = new InputOutput();
                inputOutput.write0(order[i]);
                inputOutput.write0(lastUt);
                p.run(inputOutput::read0, inputOutput::write1);
                lastUt = inputOutput.read1();
            }
            if(lastUt > maxLastUt){
                maxLastUt = lastUt;
            }

        } while (Util.nextPermutation(order));


        return maxLastUt;
    }

    private class InputOutput {
        Queue<Integer> q0 = new ArrayDeque<>();
        Queue<Integer> q1 = new ArrayDeque<>();

        Integer read0() {
            return q0.remove();
        }

        void write0(Integer value) {
            System.out.println("InputOutput.write0 " + value);
            q0.add(value);
        }

        Integer read1() {
            return q1.remove();
        }

        void write1(Integer value) {
            System.out.println("InputOutput.write1 " + value);
            q1.add(value);
        }

        public void flipflop() {
            q0 = q1;
            q1 = new ArrayDeque<>();
        }
    }

    private class Program {
        int[] initial;
        int[] g;

        public Program(String str) {
            initial = Util.findAllInts(str).stream().mapToInt(a -> a).toArray();
            reset();
        }

        public void reset() {
            g = Arrays.copyOf(initial, initial.length);
        }

        public void run(Supplier<Integer> input, Consumer<Integer> output) {
            int pos = 0;
            int lastOutput = 0;
            while (g[pos] != 99) {
                int instruction = g[pos];
                int opcode = instruction % 100;
                switch (opcode) {
                    case 1:  // Add
                        g[g[pos + 3]] = getArg(g, instruction, pos, 1) + getArg(g, instruction, pos, 2);
                        pos += 4;
                        break;
                    case 2:  // Multiply
                        g[g[pos + 3]] = (getArg(g, instruction, pos, 1)) * getArg(g, instruction, pos, 2);
                        pos += 4;
                        break;
                    case 3:  // Read
                        g[g[pos + 1]] = input.get();
                        pos += 2;
                        break;
                    case 4:  // Write
                        output.accept(getArg(g, instruction, pos, 1));
                        pos += 2;
                        break;
                    case 5:  // Jump-if-true
                        if (getArg(g, instruction, pos, 1) != 0) {
                            pos = getArg(g, instruction, pos, 2);
                        } else {
                            pos += 3;
                        }
                        break;
                    case 6:  // Jump-if-false
                        if (getArg(g, instruction, pos, 1) == 0) {
                            pos = getArg(g, instruction, pos, 2);
                        } else {
                            pos += 3;
                        }
                        break;
                    case 7:  // Less than
                        if (getArg(g, instruction, pos, 1) < getArg(g, instruction, pos, 2)) {
                            g[g[pos + 3]] = 1;
                        } else {
                            g[g[pos + 3]] = 0;
                        }
                        pos += 4;
                        break;
                    case 8:  // Equals
                        if (getArg(g, instruction, pos, 1) == getArg(g, instruction, pos, 2)) {
                            g[g[pos + 3]] = 1;
                        } else {
                            g[g[pos + 3]] = 0;
                        }
                        pos += 4;
                        break;
                    default:
                        throw new RuntimeException(String.valueOf(instruction));
                }
            }
        }

        private int getArg(int[] g, int instruction, int pos, int argNum) {
            int divide = argNum == 1 ? 100 : (argNum == 2 ? 1000 : 0);
            return (instruction / divide) % 10 == 1 ? g[pos + argNum] : g[g[pos + argNum]];
        }
    }

}
