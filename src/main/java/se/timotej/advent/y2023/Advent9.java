package se.timotej.advent.y2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Advent9 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent9().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;
        for (String str : strs) {
            svar += extrapolate(str);
        }
        return svar;
    }

    private long extrapolate(String str) {
        List<Integer> firstRow = Util.findAllInts(str);
        List<List<Integer>> g = new ArrayList<>();
        g.add(firstRow);
        List<Integer> now = firstRow;
        while (now.stream().anyMatch(item -> item != 0)) {
            List<Integer> next = new ArrayList<>();
            for (int i = 1; i < now.size(); i++) {
                next.add(now.get(i) - now.get(i - 1));
            }
            g.add(next);
            now = next;

        }
        g.get(g.size() - 1).add(0);
        for (int rowIndex = g.size() - 2; rowIndex >= 0; rowIndex--) {
            now = g.get(rowIndex);
            List<Integer> last = g.get(rowIndex + 1);
            now.add(now.get(now.size() - 1) + last.get(last.size() - 1));
        }
        return firstRow.get(firstRow.size() - 1);
    }
}
