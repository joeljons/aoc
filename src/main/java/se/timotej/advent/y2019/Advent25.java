package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Advent25 {

    public static void main(String[] args) throws IOException {
        long start = System.nanoTime();
        long svar = new Advent25().calc(Online.get());
        long duration = (System.nanoTime() - start) / 1000000;
        System.out.println("duration = " + duration);
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        Program prog = new Program(strs.get(0));
        List<String> forbidden = List.of("escape pod", "photons", "infinite loop", "molten lava", "giant electromagnet");
        List<String> stuff = new ArrayList<>();
        List<String> dirs = List.of("east", "west", "north", "south");
        Random r = new Random();
        List<String> wannaTake = new ArrayList<>();
        while (true) {
            prog.run();
            StringBuilder sb = new StringBuilder();
            while (!prog.output.isEmpty()) {
                Long output = prog.output.remove();
                sb.append((char) output.intValue());
            }
            if (sb.toString().contains("You can't go that way.")) {
                break;
            }
            if (sb.toString().contains("== Pressure-Sensitive Floor ==")) {
                if (sb.toString().contains("Alert! Droids on this ship are heavier than the detected value!\"")
                        || sb.toString().contains("Alert! Droids on this ship are lighter than the detected value!\"")) {
                    prog.reset();
                    wannaTake = new ArrayList<>();
                    for (String st : stuff) {
                        if (r.nextBoolean()) {
                            wannaTake.add(st);
                        }
                    }
                    continue;
                } else {
                    System.out.println(sb.toString());
                    return Util.findAllInts(sb.toString().substring(sb.indexOf("typing"))).get(0);
                }
            }
            for (String s : sb.toString().split("\n")) {
                if (s.startsWith("- ")) {
                    s = s.substring(2);
                    if (!dirs.contains(s) && !forbidden.contains(s) && wannaTake.contains(s)) {
                        prog.write("take " + s + "\n");
                    }
                    if (!dirs.contains(s) && !forbidden.contains(s) && !stuff.contains(s)) {
                        System.out.println("Found new stuff: " + s);
                        prog.write("take " + s + "\n");
                        stuff.add(s);
                    }
                }
            }

            String dir;
            do {
                int d = r.nextInt(4);
                dir = dirs.get(d);
            } while (!sb.toString().contains(dir));
            prog.write(dir + "\n");
        }
        return 0;
    }
}