package se.timotej.advent.y2015;

import java.io.IOException;
import java.util.List;

public class Advent8 {
    public static void main(String[] args) throws IOException {
        int svar = new Advent8().calc(Online.get(8));
        System.out.println("svar = " + svar);
        Online.submit(8, 1, svar);
    }

    int calc(List<String> strs) {
        int codeSize = 0;
        int strSize = 0;
        for (String str : strs) {
            codeSize += str.length();
            for (int i = 1; i < str.length() - 1; i++) {
                strSize++;
                if (str.charAt(i) == '\\') {
                    if (str.charAt(i + 1) == 'x') {
                        i += 3;
                    } else {
                        i++;
                    }
                }
            }
        }
        return codeSize - strSize;
    }
}
