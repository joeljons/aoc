package se.timotej.advent.y2017;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Advent7 {
    public static void main(String[] args) throws IOException {
        new Advent7().calc(Online.get(7));
    }

    String calc(List<String> str) {
        Set<String> from = new HashSet<>();
        Set<String> to = new HashSet<>();
        for (String s : str) {
            String[] split = s.split("->");
            Matcher matcher = Pattern.compile("(\\w+) \\((\\d+)\\)").matcher(split[0]);
            matcher.lookingAt();
            String namn = matcher.group(1);
            String vikt = matcher.group(2);
            from.add(namn);
            if(split.length == 2){
                for (String s1 : split[1].split(",")) {
                    String t = s1.trim();
                    to.add(t);
                }
            }
        }
        from.removeAll(to);
        for (String s : from) {
            System.out.println("s = " + s);
            return s;
        }
        return null;
    }
}
