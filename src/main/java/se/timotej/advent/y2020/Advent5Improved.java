package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.List;

public class Advent5Improved {

    public static void main(String[] args) throws IOException {
        int svar = new Advent5Improved().calc(Online.get(5));
        System.out.println("svar = " + svar);
        //Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int hi = 0;
        for (String str : strs) {
            int id = getId(str);
            if (id > hi) {
                hi = id;
            }
        }
        return hi;
    }

    private int getId(String str) {
        String binary = str
                .replaceAll("[BR]", "1")
                .replaceAll("[FL]", "0");
        return Integer.parseInt(binary, 2);
    }

}
