package se.timotej.advent.y2025;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    public static <T> List<T> findAll(String haystack, String regexp, Function<String, T> converter) {
        Matcher matcher = Pattern.compile(regexp).matcher(haystack);
        List<T> allMatches = new ArrayList<>();
        while (matcher.find()) {
            allMatches.add(converter.apply(matcher.group()));
        }
        return allMatches;
    }

    public static List<Integer> findAllInts(String haystack) {
        return findAll(haystack, "[-\\d]+", Integer::parseInt);
    }

    public static List<Integer> findAllPositiveInts(String haystack) {
        return findAll(haystack, "[\\d]+", Integer::parseInt);
    }

    public static List<Long> findAllLongs(String haystack) {
        return findAll(haystack, "[-\\d]+", Long::parseLong);
    }

    public static List<Long> findAllPositiveLongs(String haystack) {
        return findAll(haystack, "[\\d]+", Long::parseLong);
    }

    public static int[] intArray(String str) {
        String[] split = str.trim().split("[ \t]+");
        int[] array = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            array[i] = Integer.parseInt(split[i]);
        }
        return array;
    }

    public static int[] intArrayComma(String str) {
        String[] split = str.split(",");
        int[] array = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            array[i] = Integer.parseInt(split[i].trim());
        }
        return array;
    }

    public static List<List<String>> splitByDoubleNewline(List<String> strs) {
        List<List<String>> groups = new ArrayList<>();
        List<String> group = new ArrayList<>();
        for (String str : strs) {
            if (str.isEmpty()) {
                groups.add(group);
                group = new ArrayList<>();
            } else {
                group.add(str);
            }
        }
        if (!group.isEmpty()) {
            groups.add(group);
        }
        return groups;
    }

    public static int[] intArray(List<String> strs) {
        int[] array = new int[strs.size()];
        for (int i = 0; i < strs.size(); i++) {
            array[i] = Integer.parseInt(strs.get(i));
        }
        return array;
    }

    public static int[][] intMatrix(String str) {
        String[] split = str.split("\n");
        int[][] matrix = new int[split.length][];
        for (int i = 0; i < split.length; i++) {
            matrix[i] = intArray(split[i]);
        }
        return matrix;
    }

    public static int[][] intMatrix(List<String> strs) {
        int[][] matrix = new int[strs.size()][];
        for (int i = 0; i < strs.size(); i++) {
            matrix[i] = intArray(strs.get(i));
        }
        return matrix;
    }

    public static String[] stringArray(String str) {
        return str.split("\t");
    }

    public static String[][] stringMatrix(String str) {
        String[] split = str.split("\n");
        String[][] matrix = new String[split.length][];
        for (int i = 0; i < split.length; i++) {
            matrix[i] = stringArray(split[i]);
        }
        return matrix;
    }

    public static String[][] stringMatrix(List<String> strs) {
        String[][] matrix = new String[strs.size()][];
        for (int i = 0; i < strs.size(); i++) {
            matrix[i] = stringArray(strs.get(i));
        }
        return matrix;
    }
    public static char[][] charMatrix(List<String> strs) {
        char[][] matrix = new char[strs.size()][];
        for (int i = 0; i < strs.size(); i++) {
            matrix[i] = strs.get(i).toCharArray();
        }
        return matrix;
    }

    public static List<Integer> intList(int[] ints) {
        List<Integer> list = new ArrayList<>();
        for (int i : ints) {
            list.add(i);
        }
        return list;
    }

    public static boolean nextPermutation(final Comparable[] c) {
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
