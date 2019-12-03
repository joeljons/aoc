package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.List;

public class Advent3 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent3().advent1(Online.get(3));
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int g[][] = new int[1000][1000];

    private int advent1(List<String> strs) {
        int svar = 0;
        for (String str : strs) {
            String[] line = str.split("[ ,x:]");
            System.out.println("line = " + line);
            int x = Integer.parseInt(line[2]);
            int y = Integer.parseInt(line[3]);
            int w = Integer.parseInt(line[5]);
            int h = Integer.parseInt(line[6]);
            for (int X = x; X < x + w; X++) {
                for (int Y = y; Y < y + h; Y++){
                    g[Y][X]++;
                }
            }
        }
        for (int[] ints : g) {
            for (int anInt : ints) {
                if(anInt>1){
                    svar++;
                }
            }
        }

        return svar;
    }

}
