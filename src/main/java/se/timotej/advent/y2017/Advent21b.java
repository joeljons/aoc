package se.timotej.advent.y2017;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent21b {
    public static void main(String[] args) throws IOException {
        //new Advent21().calc(Collections.singletonList("3"));
        new Advent21b().calc(Online.get(21));
    }

    private void calc(List<String> strs) {
        Map<String, String> transformations = new HashMap<>();
        for (String str : strs) {
            String[] split = str.split(" => ");
            String from = split[0];
            String to = split[1];
            String[] w = from.split("\\/");
            int len = w.length;
            for (int ydir = -1; ydir <= 1; ydir += 2) {
                for (int xdir = -1; xdir <= 1; xdir += 2) {
                    StringBuilder sb = new StringBuilder();
                    for (int y = 0; y < len; y++) {
                        for (int x = 0; x < len; x++) {
                            sb.append(w[ydir == -1 ? (len - y - 1) : y].charAt(xdir == -1 ? (len - x - 1) : x));
                        }
                        if (y != len - 1) {
                            sb.append("/");
                        }
                    }
                    transformations.put(sb.toString(), to);
                }
            }
            for (int xdir = -1; xdir <= 1; xdir += 2) {
                for (int ydir = -1; ydir <= 1; ydir += 2) {
                    StringBuilder sb = new StringBuilder();
                    for (int x = 0; x < len; x++) {
                        for (int y = 0; y < len; y++) {
                            sb.append(w[ydir == -1 ? (len - y - 1) : y].charAt(xdir == -1 ? (len - x - 1) : x));
                        }
                        if (x != len - 1) {
                            sb.append("/");
                        }
                    }
                    transformations.put(sb.toString(), to);
                }
            }
        }

        List<String> now = Arrays.asList(
                ".#.",
                "..#",
                "###");
        for (int it = 0; it < 18; it++) {
            List<StringBuilder> next = new ArrayList<>();
            int its;
            int nysize;
            int itsize;
            int itsizeTo;
            if (now.size() % 2 == 0) {
                its = now.size() / 2;
                itsizeTo = 3;
                itsize = 2;
            } else {
                its = now.size() / 3;
                itsizeTo = 4;
                itsize = 3;
            }
            nysize = its * itsizeTo;

            for (int i = 0; i < nysize; i++) {
                next.add(new StringBuilder());
            }
            for (int Y = 0; Y < its; Y++) {
                for (int X = 0; X < its; X++) {
                    StringBuilder sb = new StringBuilder();
                    for (int y = 0; y < itsize; y++) {
                        for (int x = 0; x < itsize; x++) {
                            try {
                                sb.append(now.get(y + itsize * Y).charAt(x + itsize * X));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (y != itsize - 1) {
                            sb.append("/");
                        }
                    }
                    String lookup = sb.toString();
                    String lookedup = transformations.get(lookup);
                    for (int y = 0; y < itsizeTo; y++) {
                        for (int x = 0; x < itsizeTo; x++) {
                            try {
                                char c = lookedup.charAt((itsizeTo + 1) * y + x);
                                if(c == '/'){
                                    int apa = 342;
                                }
                                next.get(y+itsizeTo*Y).append(c);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            now = new ArrayList<>();
            for (StringBuilder stringBuilder : next) {
                now.add(stringBuilder.toString());
            }
        }
        int count = 0;
        for (String s : now) {
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if(c=='#')count++;
            }
        }
        System.out.println("count = " + count);
    }
}