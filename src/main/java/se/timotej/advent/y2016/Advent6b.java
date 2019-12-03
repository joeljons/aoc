package se.timotej.advent.y2016;

import java.io.IOException;
import java.util.List;

public class Advent6b {

    public static void main(String[] args) throws IOException {
        String svar = new Advent6b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private String calc(List<String> strs) {
        int[][] counts = new int[8][26];
        for (String str : strs) {
            for (int i = 0; i < 8; i++) {
                char c = str.charAt(i);
                counts[i][c - 'a']++;
            }
        }
        String pwd = "";
        for (int i = 0; i < 8; i++) {
            int vanligast = 0;
            for (int j = 0; j < 26; j++) {
                if (counts[i][j] < counts[i][vanligast]) {
                    vanligast = j;
                }
            }
            pwd += (char) (vanligast + 'a');
        }
        return pwd;
    }
}