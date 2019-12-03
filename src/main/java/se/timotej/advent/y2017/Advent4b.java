package se.timotej.advent.y2017;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent4b {
    public static void main(String[] args) throws IOException {
        new Advent4b().calc(Online.get(4));
    }

    private void calc(List<String> split) {
        int count = 0;
        for (String aSplit : split) {
            String[] rad = aSplit.split(" ");
            Set<String> set = new HashSet<>();
            for (String aRad : rad) {
                set.add(sortera(aRad));
            }
            if (rad.length == set.size()) count++;
        }
        System.out.println("count = " + count);
    }

    private String sortera(String s) {
        List<Character> list = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            list.add(s.charAt(i));
        }
        Collections.sort(list);
        StringBuilder b = new StringBuilder();
        for (Character character : list) {
            b.append(character);
        }
        return b.toString();
    }


}
