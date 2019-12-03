package se.timotej.advent.y2017;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Advent17 {
    public static void main(String[] args) throws IOException {
        new Advent17().calc(Collections.singletonList("3"));
        new Advent17().calc(Online.get(17));
    }

    private void calc(List<String> strs) {
        String str = strs.get(0);
        int stepLen = Integer.parseInt(str);
        List<Integer> g = new LinkedList<>();
        g.add(0);
        int pos = 0;
        for(int i=1;i<=2017;i++){
            pos = (pos+stepLen)%g.size();
            g.add(pos+1, i);
            pos++;
        }
        pos = (pos+1)%g.size();
        Integer svar = g.get(pos);
        System.out.println("svar = " + svar);

    }
}