package se.timotej.advent.y2022;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Advent13b {
    public static void main(String[] args) throws IOException {
        int svar = new Advent13b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int parsePos;

    private int calc(List<String> strs) {
        List<Num> nums = new ArrayList<>();
        for (String str : strs) {
            if (!str.isEmpty()) {
                parsePos = 0;
                nums.add(new Num(str));
            }
        }
        parsePos = 0;
        Num two = new Num("[[2]]");
        nums.add(two);
        parsePos = 0;
        Num six = new Num("[[6]]");
        nums.add(six);
        Collections.sort(nums);
        return (nums.indexOf(two) + 1) * (nums.indexOf(six) + 1);
    }

    private class Num implements Comparable<Num> {
        List<Num> sub;
        Integer value;

        public Num(String str) {
            char c = str.charAt(parsePos++);
            if (Character.isDigit(c)) {
                value = c - '0';
                while (Character.isDigit(str.charAt(parsePos))) {
                    c = str.charAt(parsePos++);
                    value = value * 10 + c - '0';
                }
            } else if (c == '[') {
                sub = new ArrayList<>();
                while (str.charAt(parsePos) != ']') {
                    sub.add(new Num(str));
                    if (str.charAt(parsePos) == ',') {
                        parsePos++;
                    }
                }
                parsePos++;
            } else {
                throw new RuntimeException("Unknown part: " + c);
            }
        }

        public Num(Num toBeSubListed) {
            this.sub = List.of(toBeSubListed);
        }

        @Override
        public int compareTo(Num o) {
            if (this.value != null && o.value != null) {
                return this.value.compareTo(o.value);
            } else if (this.sub != null && o.sub != null) {
                for (int i = 0; i < this.sub.size() && i < o.sub.size(); i++) {
                    int cmp = this.sub.get(i).compareTo(o.sub.get(i));
                    if (cmp != 0) {
                        return cmp;
                    }
                }
                return Integer.compare(this.sub.size(), o.sub.size());
            } else if (this.value != null && o.sub != null) {
                return new Num(this).compareTo(o);
            } else if (this.sub != null && o.value != null) {
                return this.compareTo(new Num(o));
            } else {
                throw new RuntimeException();
            }
        }
    }
}
