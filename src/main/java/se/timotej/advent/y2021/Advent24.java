package se.timotej.advent.y2021;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Advent24 {

    public static void main(String[] args) throws IOException {
        var svar = new Advent24().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    long bestest = Long.MAX_VALUE;

    String calc(List<String> strs) {
        long[] origInputs = new long[14];
        Arrays.fill(origInputs, 1);
        while (improve(origInputs, strs)) {
        }
        String sum = "";
        for (long input : origInputs) {
            sum += Long.toString(input);
        }
        return sum;
    }

    private boolean improve(long[] origInputs, List<String> strs) {
        System.out.printf("Try improve from %d %s%n", bestest, Arrays.toString(origInputs));
        boolean improved = false;
        long[] origOrigInputs = new long[14];
        System.arraycopy(origInputs, 0, origOrigInputs, 0, 14);
        long[] inputs = new long[14];
        for (int a = 0; a < 14; a++) {
            for (int b = a + 1; b < 14; b++) {
                for (int c = b + 1; c < 14; c++) {
                    System.arraycopy(origOrigInputs, 0, inputs, 0, 14);
                    for (int a1 = 1; a1 < 10; a1++) {
                        inputs[a] = a1;
                        for (int b1 = 1; b1 < 10; b1++) {
                            inputs[b] = b1;
                            for (int c1 = 1; c1 < 10; c1++) {
                                inputs[c] = c1;
                                long svar = runProg(inputs, strs);
                                if (svar < bestest || svar == bestest && Arrays.compare(inputs, origInputs) > 0) {
                                    improved = true;
                                    System.out.printf("%d %d %d%n", a, b, c);
                                    System.arraycopy(inputs, 0, origInputs, 0, 14);
                                    bestest = svar;
                                    System.out.println("bestest = " + bestest);
                                    System.out.println(Arrays.toString(inputs));
                                }
                            }
                        }
                    }
                }
            }
        }
        return improved;
    }

    private long runProg(long[] inputs, List<String> strs) {
        long[] register = new long[4];
        int inputPos = 0;
        for (String str : strs) {
            String[] line = str.split(" ");
            if (line[0].equals("inp")) {
                register[line[1].charAt(0) - 'w'] = inputs[inputPos++];
            } else if (line[0].equals("add")) {
                register[line[1].charAt(0) - 'w'] += read(register, line[2]);
            } else if (line[0].equals("mul")) {
                register[line[1].charAt(0) - 'w'] *= read(register, line[2]);
            } else if (line[0].equals("div")) {
                register[line[1].charAt(0) - 'w'] /= read(register, line[2]);
            } else if (line[0].equals("mod")) {
                register[line[1].charAt(0) - 'w'] %= read(register, line[2]);
            } else if (line[0].equals("eql")) {
                register[line[1].charAt(0) - 'w'] = read(register, line[1]) == read(register, line[2]) ? 1 : 0;
            }
        }
        return register[3];
    }

    private long read(long[] register, String s) {
        if (Character.isLetter(s.charAt(0))) {
            return register[s.charAt(0) - 'w'];
        } else {
            return Long.parseLong(s);
        }
    }


}

