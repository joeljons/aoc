package se.timotej.advent.y2021;

import org.apache.commons.lang3.tuple.Triple;

import java.io.IOException;
import java.util.List;
import java.util.TreeSet;

public class Advent22 {

    public static void main(String[] args) throws IOException {
        var svar = new Advent22().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        TreeSet<Triple<Integer, Integer, Integer>> g = new TreeSet<>();
        for (String str : strs) {
            boolean turn = str.startsWith("on");
            List<Integer> allInts = Util.findAllInts(str);
            if (Math.abs(allInts.get(0)) > 50) {
                break;
            }
            for (int x = allInts.get(0); x <= allInts.get(1); x++) {
                for (int y = allInts.get(2); y <= allInts.get(3); y++) {
                    for (int z = allInts.get(4); z <= allInts.get(5); z++) {
                        if (turn) {
                            g.add(Triple.of(x, y, z));
                        } else {
                            g.remove(Triple.of(x, y, z));
                        }
                    }
                }
            }
        }
        return g.size();
    }

}
