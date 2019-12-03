package se.timotej.advent.y2015;

import java.io.IOException;
import java.util.List;

public class Advent17 {
    public static void main(String[] args) throws IOException {
        int svar = new Advent17().calc(Online.get(17));
        System.out.println("svar = " + svar);
        Online.submit(17, 1, svar);
    }

    int[] g = new int[1000];

    int calc(List<String> strs) {
        g[0] = 1;
        for (int size : Util.intArray(strs)) {
            for(int i=150;i>=0;i--){
                g[i+size] += g[i];
            }
        }
        return g[150];
    }
}
