package se.timotej.advent.y2022;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent3b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent3b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int sum = 0;
        for (int i = 0; i < strs.size(); i += 3) {
            List<Set<Character>> apa = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                Set<Character> chars = new HashSet<>();
                for (int k = 0; k < strs.get(i + j).length(); k++) {
                    chars.add(strs.get(i + j).charAt(k));
                }
                apa.add(chars);
            }
            Set<Character> now = apa.get(0);
            now.retainAll(apa.get(1));
            now.retainAll(apa.get(2));
            for (Character character : now) {
                if (Character.isLowerCase(character)) {
                    sum += character - 'a' + 1;
                } else {
                    sum += character - 'A' + 27;
                }
            }
        }
        return sum;
    }

}
