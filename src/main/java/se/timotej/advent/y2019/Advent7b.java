package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Advent7b {

    public static void main(String[] args) throws IOException, InterruptedException {
        int svar = new Advent7b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) throws InterruptedException {
        Integer[] order = new Integer[]{5, 6, 7, 8, 9};
        Program[] p = new Program[]{
                new Program(strs.get(0)),
                new Program(strs.get(0)),
                new Program(strs.get(0)),
                new Program(strs.get(0)),
                new Program(strs.get(0))};
        int maxLastUt = 0;
        do {
            InputOutput inputOutput = new InputOutput();
            for (int i = 0; i < 5; i++) {
                p[i].reset();
                inputOutput.write(i, order[i]);
            }
            Thread[] threads = new Thread[5];
            inputOutput.write(0, 0);
            for (int i = 0; i < 5; i++) {
                int finali = i;
                threads[i] = new Thread(() -> {
                    p[finali].run(() -> inputOutput.read(finali),
                            ut -> inputOutput.write((finali + 1) % 5, ut));
                });
                threads[i].start();
            }
            for (int i = 0; i < 5; i++) {
                threads[i].join();
            }
            int lastUt = inputOutput.q.get(0).poll();
            if (lastUt > maxLastUt) {
                maxLastUt = lastUt;
            }

        } while (Util.nextPermutation(order));


        return maxLastUt;
    }

    private class InputOutput {
        List<BlockingQueue<Integer>> q = new ArrayList<>();

        public InputOutput() {
            for (int i = 0; i < 5; i++) {
                q.add(new LinkedBlockingQueue<>());
            }
        }

        Integer read(int qi) {
            try {
                return q.get(qi).take();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(0);
                return 0;
            }
        }

        void write(int qi, Integer value) {
            q.get(qi).add(value);
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
