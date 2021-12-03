package se.timotej.advent.y2021;

import java.io.IOException;
import java.util.List;

public class Advent3 {

    public static void main(String[] args) throws IOException {
        var svar = new Advent3().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int gamma = 0;
        int epsilon = 0;
        int len = strs.get(0).length();
        int mul = 1;
        for (int i = len - 1; i >= 0; i--) {
            int noll = 0;
            int ett = 0;
            for (String str : strs) {
                if (str.charAt(i) == '1') {
                    ett++;
                } else {
                    noll++;
                }
            }
            if (ett > noll) {
                gamma += mul;
            } else {
                epsilon += mul;
            }
            mul *= 2;
        }
        return gamma * epsilon;
    }

}
