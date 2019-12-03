package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.List;

public class Advent6b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent6b().calc(Online.get());

        System.out.println("svar = " + svar);
        //Online.submit(svar);
    }


    private int calc(List<String> strs) {
        int[] xDist = new int[400];
        int[] yDist = new int[400];
        for (String str : strs) {
            String[] line = str.split("[, ]+");
            int x = Integer.parseInt(line[0]);
            int y = Integer.parseInt(line[1]);
            for (int i = 0; i < 400; i++) {
                xDist[i] += Math.abs(i - x);
                yDist[i] += Math.abs(i - y);
            }
        }
        int total = 0;
        for (int y = 0; y < 400; y++) {
            for (int x = 0; x < 400; x++) {
                if ((xDist[y] + yDist[x]) < 10000) {
                    total++;
                }
            }
        }
        return total;
    }
}
