package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.List;

public class Advent19b_2 {

    public static void main(String[] args) throws IOException {
        String svar = new Advent19b_2().calc(Online.get(19));
        System.out.println("svar = " + svar);
        Online.submit(19, 2, svar);
    }


    private String calc(List<String> strs) {
        int svar = 0;
        for (int i = 1; i <= 10551374; i++){
            if (10551374 % i == 0) {
                svar += i;
            }
        }
        return String.valueOf(svar);
    }
}