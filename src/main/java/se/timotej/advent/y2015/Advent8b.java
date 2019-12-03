package se.timotej.advent.y2015;

import java.io.IOException;
import java.util.List;

public class Advent8b {
    public static void main(String[] args) throws IOException {
        int svar = new Advent8b().calc(Online.get(8));
        System.out.println("svar = " + svar);
        Online.submit(8, 2, svar);
    }

    int calc(List<String> strs) {
        int codeSize = 0;
        int newCode = 0;
        for (String str : strs) {
            newCode+=2;
            codeSize += str.length();
            for (int i = 0; i < str.length(); i++) {
                newCode++;
                if (str.charAt(i) == '\\' || str.charAt(i) == '"') {
                    newCode++;
                }
            }
        }
        return newCode-codeSize;
    }
}
