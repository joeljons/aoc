package se.timotej.advent.y2015;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Advent13 {
    public static void main(String[] args) throws IOException {
        int svar = new Advent13().calc(Online.get(13));
        System.out.println("svar = " + svar);
        Online.submit(13, 1, svar);
    }

    int calc(List<String> strs) {
        Set<String> namn = new HashSet<>();
        Map<Pair<String, String>, Integer> g = new HashMap<>();
        for (String str : strs) {
            str = str.replace(".", "");
            String[] line = str.split(" ");
            g.put(Pair.of(line[0], line[10].substring(0)), Integer.parseInt(line[3]) * (line[2].equals("gain") ? 1 :
                    -1));
            namn.add(line[0]);
        }
        ArrayList<String> list = new ArrayList<>(namn);
        String[] array = list.toArray(new String[0]);
        Arrays.sort(array);
        int best = 0;
        do {
            int score = 0;
            for (int i = 0; i < array.length; i++) {
                score += g.get(Pair.of(array[i], array[(i + 1) % array.length]));
                score += g.get(Pair.of(array[(i + 1) % array.length], array[i]));
            }
            if (score > best) {
                best = score;
                System.out.println("best = " + best);
                System.out.println("array = " + Arrays.toString(array));
            }
        } while (nextPermutation(array));
        return best;
    }

    // modifies c to next permutation or returns null if such permutation does not exist
    private static boolean nextPermutation(final Comparable[] c) {
        // 1. finds the largest k, that c[k] < c[k+1]
        int first = getFirst(c);
        if (first == -1) {
            return false; // no greater permutation
        }
        // 2. find last index toSwap, that c[k] < c[toSwap]
        int toSwap = c.length - 1;
        while (c[first].compareTo(c[toSwap]) >= 0) {
            --toSwap;
        }
        // 3. swap elements with indexes first and last
        swap(c, first++, toSwap);
        // 4. reverse sequence from k+1 to n (inclusive)
        toSwap = c.length - 1;
        while (first < toSwap) {
            swap(c, first++, toSwap--);
        }
        return true;
    }

    // finds the largest k, that c[k] < c[k+1]
    // if no such k exists (there is not greater permutation), return -1
    private static int getFirst(final Comparable[] c) {
        for (int i = c.length - 2; i >= 0; --i) {
            if (c[i].compareTo(c[i + 1]) < 0) {
                return i;
            }
        }
        return -1;
    }

    // swaps two elements (with indexes i and j) in array
    private static void swap(final Comparable[] c, final int i, final int j) {
        final Comparable tmp = c[i];
        c[i] = c[j];
        c[j] = tmp;
    }
}
