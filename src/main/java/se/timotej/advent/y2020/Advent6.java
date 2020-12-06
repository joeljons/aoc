package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent6 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent6().calc(Online.get());
        System.out.println("svar = " + svar);

        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int sum = 0;
        Set<Character> q = new HashSet<>();
        for (String str : strs) {
            if(str.isEmpty()){
                sum += q.size();
                q = new HashSet<>();
            }
            for(int i = 0; i < str.length(); i++) {
              char c = str.charAt(i);
                q.add(c);
            }
        }
        sum += q.size();
        return sum;
    }



}
