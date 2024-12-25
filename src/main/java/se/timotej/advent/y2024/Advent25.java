package se.timotej.advent.y2024;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Advent25 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent25().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;

        List<List<String>> inputs = Util.splitByDoubleNewline(strs);
        List<List<String>> locks = new ArrayList<>();
        List<List<String>> keys = new ArrayList<>();
        for (List<String> input : inputs) {
            if (input.getFirst().equals("#####")) {
                locks.add(input);
            } else {
                keys.add(input);
            }
        }
        for (List<String> lock : locks) {
            for (List<String> key : keys) {
                if (fits(lock, key)) {
                    svar++;
                }
            }
        }

        return svar;
    }

    private static boolean fits(List<String> lock, List<String> key) {
        for (int y = 0; y < lock.size(); y++) {
            for (int x = 0; x < lock.get(y).length(); x++) {
                if (lock.get(y).charAt(x) == '#' && key.get(y).charAt(x) == '#') {
                    return false;
                }
            }
        }
        return true;
    }

}
