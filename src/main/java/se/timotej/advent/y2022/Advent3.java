package se.timotej.advent.y2022;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent3 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent3().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int sum = 0;
        for (String str : strs) {
            Set<Character> left = new HashSet<>();
            Set<Character> right = new HashSet<>();
            for (int i = 0; i < str.length() / 2; i++) {
                left.add(str.charAt(i));
                right.add(str.charAt(i + str.length() / 2));
            }
            left.retainAll(right);
            for (Character character : left) {
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
