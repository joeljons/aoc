package se.timotej.advent.y2017;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static int[] intArray(String str) {
        String[] split = str.split("\t");
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

    public static List<Integer> intList(int[] ints) {
        List<Integer> list = new ArrayList<>();
        for (int i : ints) {
            list.add(i);
        }
        return list;
    }
}
