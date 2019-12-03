package se.timotej.advent.y2017;

import java.io.IOException;
import java.util.List;

import static java.util.Collections.singletonList;

public class Advent9 {
    public static void main(String[] args) throws IOException {
        new Advent9().calc(singletonList("<>"));
        new Advent9().calc(singletonList("<random characters>"));
        new Advent9().calc(singletonList("<<<<>"));
        new Advent9().calc(singletonList("<{!>}>"));
        new Advent9().calc(singletonList("<!!>"));
        new Advent9().calc(singletonList("<!!!>>"));
        new Advent9().calc(singletonList("<{o\"i!a,<{i<a>"));

        new Advent9().calc(singletonList("{}"));
        new Advent9().calc(singletonList("{{{}}}"));
        new Advent9().calc(singletonList("{{},{}}"));
        new Advent9().calc(singletonList("{{{},{},{{}}}}"));
        new Advent9().calc(singletonList("{<a>,<a>,<a>,<a>}"));
        new Advent9().calc(singletonList("{{<ab>},{<ab>},{<ab>},{<ab>}}"));
        new Advent9().calc(singletonList("{{<!!>},{<!!>},{<!!>},{<!!>}}"));
        new Advent9().calc(singletonList("{{<a!>},{<a!>},{<a!>},{<ab>}}"));
        new Advent9().calc(Online.get(9));
    }

    private void calc(List<String> strs) {
        String str = strs.get(0);
        int depth = 0;
        int score = 0;
        int gc = 0;
        boolean garbage = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '!') {
                i++;
            }else if (c == '>') {
                garbage = false;
            } else if (garbage) {
                gc++;
            } else if (c == '<') {
                garbage = true;
            }  else if (c == '{') {
                depth++;
            } else if (c == '}') {
                score += depth;
                depth--;
            }
        }
        if(str.length()<1000)
        System.out.println(str);
        System.out.println("score = " + score);
        System.out.println("gc = " + gc);
        System.out.println();
    }


}
