package se.timotej.advent.y2023;

import org.apache.commons.lang3.tuple.Triple;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent12b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent12b().calc(Online.get());
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
        List<Integer> groups = Util.findAllInts(split[1] + "," + split[1] + "," + split[1] + "," + split[1] + "," + split[1]);
        return rek(0, (split[0] + "?" + split[0] + "?" + split[0] + "?" + split[0] + "?" + split[0]).toCharArray(), groups, 0, 0, new HashMap<>());
    }

    private long rek(int i, char[] s, List<Integer> groups, int groupI, int lastSize, Map<Triple<Integer, Integer, Integer>, Long> g) {
        var key = Triple.of(i, groupI, lastSize);
        if (g.containsKey(key)) {
            return g.get(key);
        }
        long sum;
        if (i == s.length) {
            if (groupI == groups.size() && lastSize == 0) {
                sum = 1;
            } else if (groupI == groups.size() - 1 && groups.get(groupI) == lastSize) {
                sum = 1;
            } else {
                sum = 0;
            }
        } else {
            if (groupI == groups.size() && lastSize != 0) {
                sum = 0;
            } else if (s[i] == '?') {
                sum = rek(i + 1, s, groups, groupI, lastSize + 1, g);
                if (lastSize == 0) {
                    sum += rek(i + 1, s, groups, groupI, 0, g);
                } else if (groupI < groups.size() && groups.get(groupI) == lastSize) {
                    sum += rek(i + 1, s, groups, groupI + 1, 0, g);
                }
            } else {
                if (s[i] == '#') {
                    sum = rek(i + 1, s, groups, groupI, lastSize + 1, g);
                } else if (s[i] == '.') {
                    if (lastSize == 0) {
                        sum = rek(i + 1, s, groups, groupI, 0, g);
                    } else if (groupI < groups.size() && groups.get(groupI) == lastSize) {
                        sum = rek(i + 1, s, groups, groupI + 1, 0, g);
                    } else {
                        sum = 0;
                    }
                } else {
                    throw new RuntimeException();
                }
            }
        }
        g.put(key, sum);
        return sum;
    }
}
