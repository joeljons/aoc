package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Advent10 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent10().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int[] ints = Util.intArray(strs);
        Arrays.sort(ints);
        int ett = 0;
        int tre = 0;
        int last = 0;
        for (int nu : ints) {
            if (nu - last == 1) {
                ett++;
            }
            if (nu - last == 3) {
                tre++;
            }
            last = nu;
        }
        tre++;
        return ett*tre;
    }
}
