package se.timotej.advent.y2016;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent7b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent7b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int count = 0;
        for (String str : strs) {
            boolean inne = false;
            Set<String> innes = new HashSet<>();
            Set<String> utes = new HashSet<>();
            for (int i = 0; i < str.length() - 2; i++) {
                char c = str.charAt(i);
                if (str.charAt(i) == str.charAt(i + 2) && str.charAt(i + 1) != str.charAt(i)) {
                    if (inne) {
                        innes.add("" + str.charAt(i + 1) + str.charAt(i) + str.charAt(i + 1));
                    } else {
                        utes.add(str.substring(i, i + 3));
                    }
                }
                if (c == '[') {
                    inne = true;
                }
                if (c == ']') {
                    inne = false;
                }
            }
            if (innes.removeAll(utes)) {
                count++;
            }
        }
        return count;
    }
}
