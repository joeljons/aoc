package se.timotej.advent.y2021;

import java.io.IOException;
import java.util.List;

public class Advent2b {

    public static void main(String[] args) throws IOException {
        var svar = new Advent2b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int x = 0;
        int z = 0;
        int aim = 0;
        for (String str : strs) {
            String[] line = str.split(" ");
            int d = Integer.parseInt(line[1]);
            if (line[0].equals("forward")) {
                x += d;
                z += aim * d;
            }
            if (line[0].equals("down")) {
                aim += d;
            }
            if (line[0].equals("up")) {
                aim -= d;
            }
        }
        return x * z;
    }

}
