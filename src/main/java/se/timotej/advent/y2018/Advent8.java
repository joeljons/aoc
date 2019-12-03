package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.List;

public class Advent8 {

    private int[] g;

    public static void main(String[] args) throws IOException {
        int svar = new Advent8().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int pos = 0;

    private int calc(List<String> strs) {
        g = Util.intArray(strs.get(0));
        int svar = rek();

        return svar;
    }

    private int rek() {
        int children = g[pos++];
        int metadatas = g[pos++];
        int svar = 0;
        for (int i = 0; i < children; i++) {
            svar += rek();
        }
        for (int i = 0; i < metadatas; i++) {
            svar += g[pos++];
        }
        return svar;
    }

}
