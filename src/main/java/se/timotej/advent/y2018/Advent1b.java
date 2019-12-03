package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent1b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent1b().advent1b(Online.get(1));
        System.out.println("svar = " + svar);
        //Online.submit(1, 2, svar);
    }

    private int advent1b(List<String> strs) {
        Set<Integer> found = new HashSet<>();
        int sum = 0;
        int loops=0;
        while(true) {
            loops++;
            System.out.println("loops = " + loops);
            for (Integer i : Util.intArray(strs)) {
                sum += i;
                if (!found.add(sum)) {
                    return sum;
                }
            }
        }

    }

}
