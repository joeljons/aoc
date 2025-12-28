package se.timotej.advent.y2025;

import java.io.IOException;
import java.util.List;

public class Advent12 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent12().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] presentSize;

    private int calc(List<String> strs) {
        List<List<String>> input = Util.splitByDoubleNewline(strs);
        presentSize = new int[input.size() - 1];

        for (int i = 0; i < input.size() - 1; i++) {
            for (String row : input.get(i).subList(1, input.get(i).size())) {
                for (int col = 0; col <row.length(); col++) {
                    if (row.charAt(col) == '#') {
                        presentSize[i]++;
                    }
                }
            }
        }

        int svar = 0;
        for (String s : input.getLast()) {
            if (canFit(s)) {
                svar++;
            }
        }
        return svar;
    }

    private boolean canFit(String s) {
        List<Integer> allInts = Util.findAllInts(s);
        int easyFitPresentCount = (allInts.get(0) / 3) * (allInts.get(1) / 3);
        int regionSize = allInts.get(0) * allInts.get(1);
        int totalPresents = allInts.subList(2, allInts.size()).stream().mapToInt(Integer::intValue).sum();
        if (easyFitPresentCount >= totalPresents) {
            return true;
        } else {
            int pixelsNeeded = 0;
            for (int i = 0; i < presentSize.length; i++) {
                pixelsNeeded += presentSize[i] * allInts.get(i + 2);
            }
            if (regionSize >= pixelsNeeded) {
                throw new RuntimeException("This could be hard to calculate");
            } else {
                return false;
            }
        }
    }
}
