package se.timotej.advent.y2021;

import java.io.IOException;
import java.util.List;

public class Advent8 {

    public static void main(String[] args) throws IOException {
        var svar = new Advent8().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
       int sum=0;
        for (String str : strs) {
            String[] split = str.split(" \\| ");
            String[] s2 = split[1].split(" ");
            for (String s : s2) {
                if(s.length() == 2 || s.length() == 3 || s.length() == 4|| s.length() == 7)
                    sum++;
            }
        }
       return sum;
    }

}
