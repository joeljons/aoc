package se.timotej.advent.y2015;

import java.io.IOException;
import java.util.List;

public class Advent5 {
    public static void main(String[] args) throws IOException {
        new Advent5().checkNice("ugknbfddgicrmopn");
        new Advent5().checkNice("jchzalrnumimnmhp");
        new Advent5().checkNice("haegwjzuvuyypxyu");
        new Advent5().checkNice("dvszwmarrgswjxmb");

        //new Advent5().calc(Online.get(5));
        new Advent5().calcb(Online.get(5));
    }

    private void checkNice(String str) {
        System.out.println("str = " + str);
        System.out.println("isNice(str) = " + isNice(str));
    }

    private void calc(List<String> strs) {
        int count = 0;
        for (String str : strs) {
            if (isNice(str)) {
                count++;
            }
        }
        System.out.println("count = " + count);
        Online.submit(5, 1, count);
    }

    private boolean isNice(String str) {
        int wows = 0;
        boolean twice = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                wows++;
            }
            if (i > 0 && c == str.charAt(i - 1)) {
                twice = true;
            }
        }
        return wows >= 3 && twice && !(str.contains("ab") || str.contains("cd") || str.contains("pq") || str.contains("xy"));
    }


    private void calcb(List<String> strs) {
        int count = 0;
        for (String str : strs) {
            if (isNiceb(str)) {
                count++;
            }
        }
        System.out.println("count = " + count);
        Online.submit(5, 2, count);
    }

    private boolean isNiceb(String str) {
        boolean twice = false;
        for (int i = 0; i < str.length() - 1; i++) {
            String s = str.substring(i, i + 2);
            if (str.substring(i + 2).contains(s)) {
                twice = true;
            }
        }
        boolean rep = false;
        for (int i = 0; i < str.length() - 2; i++) {
            if (str.charAt(i) == str.charAt(i + 2)) {
                rep = true;
            }
        }
        return twice && rep;
    }

}
