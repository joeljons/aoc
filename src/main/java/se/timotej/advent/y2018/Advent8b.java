package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Advent8b {

    private int[] g;

    public static void main(String[] args) throws IOException {
        int svar = new Advent8b().calc(Online.get());
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
        if (children == 0) {
            for (int i = 0; i < metadatas; i++) {
                svar += g[pos++];
            }
        } else {
            List<Integer> childValues = new ArrayList<>();
            for (int i = 0; i < children; i++) {
                childValues.add(rek());
            }
            for (int i = 0; i < metadatas; i++) {
                int apa = g[pos++];
                apa--;
                if (apa >= 0 && apa < childValues.size()) {
                    svar += childValues.get(apa);
                }
            }
        }
        return svar;
    }

}
