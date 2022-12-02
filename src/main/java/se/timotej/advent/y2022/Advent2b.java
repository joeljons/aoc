package se.timotej.advent.y2022;

import java.io.IOException;
import java.util.List;

public class Advent2b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent2b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int sum = 0;
        for (String str : strs) {
            int opponent = str.charAt(0) - 'A';
            int strategy = str.charAt(2) - 'X';
            int elf = (opponent + strategy + 2) % 3;
            sum += elf + 1;
            if (opponent == elf) {
                sum += 3;
            } else if ((opponent + 1) % 3 == elf) {
                sum += 6;
            }
        }
        return sum;
    }

}
