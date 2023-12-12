package se.timotej.advent.y2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Advent12 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent12().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;
        for (String str : strs) {
            long check = check(str);
//            System.out.println("str = " + str);
//            System.out.println("check = " + check);
//            System.out.println();
            svar += check;
        }
        return svar;
    }

    private long check(String str) {
        String[] split = str.split(" ");
        List<Integer> groups = Util.findAllInts(split[1]);
        return rek(0, split[0].toCharArray(), groups);
    }

    private long rek(int i, char[] s, List<Integer> groups) {
        if (i == s.length) {
            List<Integer> nowGroups = new ArrayList<>();
            char last = s[0];
            int groupSize = 1;
            for (int j = 1; j < s.length; j++) {
                if (s[j] == last) {
                    groupSize++;
                } else {
                    if (last == '#') {
                        nowGroups.add(groupSize);
                    }
                    groupSize = 1;
                    last = s[j];
                }
            }
            if (last == '#') {
                nowGroups.add(groupSize);
            }
            return groups.equals(nowGroups) ? 1 : 0;
        } else {
            if (s[i] == '?') {
                long sum = 0;
                s[i] = '#';
                sum += rek(i + 1, s, groups);
                s[i] = '.';
                sum += rek(i + 1, s, groups);
                s[i] = '?';
                return sum;
            } else {
                return rek(i + 1, s, groups);
            }
        }
    }
}
