package se.timotej.advent.y2023;

import java.io.IOException;
import java.util.List;

public class Advent2b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent2b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int svar = 0;
        for (String game : strs) {
            String[] split1 = game.split(":");
            String[] split2 = split1[1].split(";");
            int red = 0;
            int green = 0;
            int blue = 0;
            for (String set : split2) {
                String[] split3 = set.split(",");
                for (String qube : split3) {
                    Integer qubeCount = Util.findAllInts(qube).get(0);
                    if (qube.contains("red")) {
                        red = Math.max(red, qubeCount);
                    }
                    if (qube.contains("green")) {
                        green = Math.max(green, qubeCount);
                    }
                    if (qube.contains("blue")) {
                        blue = Math.max(blue, qubeCount);
                    }
                }
            }
            svar += red * green * blue;
        }
        return svar;
    }
}
