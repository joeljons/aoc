package se.timotej.advent.y2016;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Advent4b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent4b().calc(Online.get());
        System.out.println("svar = " + svar);
        //Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int svar = 0;
        Pattern pattern = Pattern.compile("(\\D+)(\\d+)\\[(\\w+)]");

        for (String str : strs) {
            Matcher matcher = pattern.matcher(str.replace("-",""));
            matcher.matches();
            String letters = matcher.group(1);
            String room = matcher.group(2);
            String checksum = matcher.group(3);
            if(check(letters).equals(checksum)){
                int roomNr = Integer.parseInt(room);
                svar += roomNr;
                for (int i = 0; i < str.length(); i++) {
                    char c = str.charAt(i);
                    if(c>='a' && c<='z'){
                        System.out.print((char) (((c - 'a') + roomNr) % 26 + 'a'));
                    } else {
                        System.out.print(c);
                    }
                }
                System.out.println();
            }
        }
        return svar;
    }

    private String check(String letters) {
        int g[] = new int[26];
        for (int i = 0; i < letters.length(); i++) {
            char c = letters.charAt(i);
            g[c-'a']++;
        }
        List<Pair<Integer,Character>> g2 = new ArrayList<>();
        for (int i = 0; i < g.length; i++) {
            int antal = g[i];
            g2.add(Pair.of(-antal, (char)('a' + i)));
        }
        Collections.sort(g2);
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<5;i++){
            sb.append(g2.get(i).getRight());
        }
        return sb.toString();
    }
}