package se.timotej.advent.y2015;

import java.io.IOException;

public class Advent12 {
    public static void main(String[] args) throws IOException {
        int svar = new Advent12().calc(Online.get(12).get(0));
        System.out.println("svar = " + svar);
        Online.submit(12, 1, svar);
    }

    private int calc(String str) {
        String[] split = str.split("[^-\\d]+");
        int sum=0;
        for (String s : split) {
            if(!s.isEmpty()){
                sum += Integer.parseInt(s);
            }
        }
        return sum;
    }

}