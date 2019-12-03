package se.timotej.advent.y2015;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent3 {
    public static void main(String[] args) throws IOException {
        new Advent3().calc(Online.get(3));
    }

    private void calc(List<String> strs) {
        Set<Pair<Integer, Integer>> g = new HashSet<>();
        int x=0;
        int y=0;
        g.add(Pair.of(x, y));
        String str = strs.get(0);
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if(c=='<'){
                x--;
            }else if(c=='>'){
                x++;
            }else if(c=='v'){
                y--;
            }else if(c=='^'){
                y++;
            }
            g.add(Pair.of(x, y));
        }
        System.out.println("g.size() = " + g.size());
    }

}
