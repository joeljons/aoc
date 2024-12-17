package se.timotej.advent.y2024;

import java.io.IOException;
import java.util.List;
import java.util.stream.LongStream;

public class Advent17bBruteForce {

    public static void main(String[] args) throws IOException {
        new Advent17bBruteForce().calc(Online.get(17));
    }

    private void calc(List<String> strs) {
        int[] prog = Util.findAllInts(strs.get(4)).stream().mapToInt(Integer::intValue).toArray();
        for (long rangeStart = 0; ; rangeStart++) {
            System.out.println("rangeStart = " + rangeStart);
            LongStream.range(rangeStart * 1000, (rangeStart + 1) * 1000).parallel().forEach(i -> {
                for (long astart = i * 100000000L; astart < (i + 1) * 100000000L; astart++) {
                    long a = astart;
                    long b;
                    long c;
                    int outputIndex = 0;

                    do {
                        b = a % 8;
                        b ^= 2;
                        c = a >> b;
                        b ^= c;
                        a >>= 3;
                        b ^= 7;
                        if (outputIndex >= prog.length) {
                            break;
                        }
                        int output = (int) (b % 8);
                        if (prog[outputIndex] != output) {
                            break;
                        }
                        outputIndex++;
                    } while (a != 0);

                    if (outputIndex == prog.length) {
                        System.out.println("astart = " + astart);
                        Online.submit(17, 2, astart);
                        System.exit(0);
                    }
                }
            });
        }
    }
}
