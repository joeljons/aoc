package se.timotej.advent.y2021;

import java.io.IOException;
import java.util.List;

public class Advent2 {

    public static void main(String[] args) throws IOException {
        var svar = new Advent2().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int x = 0;
        int z = 0;
        for (String str : strs) {
            String[] line = str.split(" ");
            int d = Integer.parseInt(line[1]);
            if (line[0].equals("forward")) {
                x += d;
            }
            if (line[0].equals("down")) {
                z += d;
            }
            if (line[0].equals("up")) {
                z -= d;
            }
        }
        return x * z;
    }

}
