package se.timotej.advent.y2017;

import java.io.IOException;
import java.util.List;

public class Advent5 {
    public static void main(String[] args) throws IOException {
        new Advent5().calc(Online.get(5));
        new Advent5().calcB(Online.get(5));
    }

    private void calc(List<String> str) {
        int[] ints = Util.intArray(str);
        int count = 0;
        int pos = 0;
        while (pos >= 0 && pos < ints.length) {
            int newpos = pos + ints[pos];
            ints[pos]++;
            pos = newpos;
            count++;
        }
        System.out.println("count = " + count);
    }

    private void calcB(List<String> str) {
        int[] ints = Util.intArray(str);
        int count = 0;
        int pos = 0;
        while (pos >= 0 && pos < ints.length) {
            int newpos = pos + ints[pos];
            if (ints[pos] >= 3) {
                ints[pos]--;
            } else {
                ints[pos]++;
            }
            pos = newpos;
            count++;
        }
        System.out.println("count = " + count);
    }


}
