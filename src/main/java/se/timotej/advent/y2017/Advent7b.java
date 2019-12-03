package se.timotej.advent.y2017;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Advent7b {
    public Advent7b() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        new Advent7b().calc(Online.get(7));
    }

    String root = new Advent7().calc(Online.get(7));

    Map<String, Program> g = new HashMap<>();

    private void calc(List<String> str) {
        for (String s : str) {
            String[] split = s.split("->");
            Matcher matcher = Pattern.compile("(\\w+) \\((\\d+)\\)").matcher(split[0]);
            matcher.lookingAt();
            String namn = matcher.group(1);
            Program program = new Program();
            String vikt = matcher.group(2);
            program.vikt = Integer.parseInt(vikt);
            if (split.length == 2) {
                for (String s1 : split[1].split(",")) {
                    String t = s1.trim();
                    program.children.add(t);
                }
            }
            g.put(namn, program);
        }
        int i = 0;
        for (Program program : g.values()) {
            i++;
            if (i % 100 == 0) {
                System.out.println("i = " + i);
            }
            int orig = program.vikt;
            for (int v = 1; v < 2000; v++) {
                program.vikt = v;
                for (Program p2 : g.values()) {
                    p2.cacheVikt = -1;
                }
                if (g.get(root).funkar()) {
                    System.out.println("v = " + v);
                }
            }
            program.vikt = orig;
        }
    }


    public class Program {
        int vikt;
        List<String> children = new ArrayList<>();
        int cacheVikt = -1;

        int getTotVikt() {
            if (cacheVikt >= 0) {
                return cacheVikt;
            }
            cacheVikt = vikt;
            for (String child : children) {
                cacheVikt += g.get(child).getTotVikt();
            }
            return cacheVikt;
        }

        boolean funkar() {
            if (children.isEmpty()) {
                return true;
            }
            int first = g.get(children.get(0)).getTotVikt();
            for (String child : children) {
                if (first != g.get(child).getTotVikt()) {
                    return false;
                }
                if (!g.get(child).funkar()) {
                    return false;
                }
            }
            return true;
        }

    }
}
