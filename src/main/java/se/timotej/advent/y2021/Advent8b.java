package se.timotej.advent.y2021;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent8b {

    public static void main(String[] args) throws IOException {
        var svar = new Advent8b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    String[] siffror = new String[]{
            "abcefg",
            "cf",
            "acdeg",
            "acdfg",
            "bcdf",
            "abdfg",
            "abdefg",
            "acf",
            "abcdefg",
            "abcdfg",
    };

    private int calc(List<String> strs) {
        int sum = 0;
        for (String str : strs) {
            Integer[] g = new Integer[7];
            for (int i = 0; i < 7; i++) {
                g[i] = i;
            }
            String[] split = str.split(" \\| ");
            String[] s1 = split[0].split(" ");
            String[] s2 = split[1].split(" ");
            do {
                Map<String, Integer> mapping = new HashMap<>();
                for (String s : s1) {
                    char[] siffra = new char[s.length()];
                    for (int i = 0; i < s.length(); i++) {
                        char c = s.charAt(i);
                        siffra[i] = (char) (g[c - 'a'] + 'a');
                    }
                    Arrays.sort(siffra);
                    String strSiffra = new String(siffra);
                    char[] origSiffra = s.toCharArray();
                    Arrays.sort(origSiffra);
                    String strOrigSiffra = new String(origSiffra);
                    boolean found = false;
                    for (int i = 0; i < 10; i++) {
                        if (siffror[i].equals(strSiffra)) {
                            mapping.put(strOrigSiffra, i);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        mapping = null;
                        break;
                    }
                }
                if (mapping != null) {
                    int mul = 1;
                    int tal = 0;
                    for (int i = s2.length - 1; i >= 0; i--) {
                        char[] siffraParts = s2[i].toCharArray();
                        Arrays.sort(siffraParts);
                        tal += mul * mapping.get(new String(siffraParts));
                        mul *= 10;
                    }
                    sum += tal;
                    break;
                }
            } while (Util.nextPermutation(g));

        }
        return sum;
    }

}
