package se.timotej.advent.y2017;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent4 {
    public static void main(String[] args) throws IOException {
        new Advent4().calc(Online.get(4));
    }

    private void calc(List<String> split) {
        int count = 0;
        for (String aSplit : split) {
            String[] rad = aSplit.split(" ");
            Set<String> set = new HashSet<>();
            Collections.addAll(set, rad);
            if (rad.length == set.size()) count++;
        }
        System.out.println("count = " + count);
    }



}
