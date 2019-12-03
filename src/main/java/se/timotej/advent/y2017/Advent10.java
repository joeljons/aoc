package se.timotej.advent.y2017;

import java.io.IOException;
import java.util.List;

public class Advent10 {
    public static void main(String[] args) throws IOException {
//        new Advent10().calc(5, singletonList("3,4,1,5"));
        new Advent10().calc(256, Online.get(10));
    }

    int g[];
    int listLen;

    private void calc(int listLen, List<String> strs) {
        this.listLen = listLen;
        g = new int[listLen];
        for (int i = 0; i < listLen; i++) {
            g[i] = i;
        }
        String str = strs.get(0);
        int[] lengths = Util.intArrayComma(str);
        int pos = 0;
        int skip = 0;
        for (int length : lengths) {
            reverse(pos, length);
            pos += length + skip;
            pos %= listLen;

            for (int i = 0; i < listLen; i++) {
                if (pos == i) {
                    System.out.print("[");
                }
                System.out.print(g[i]);
                if (pos == i) {
                    System.out.print("]");
                }
                System.out.print(" ");
            }
            System.out.println();
            skip++;
        }
        System.out.println("g[pos]*g[(pos+1)%listLen] = " + g[0] * g[( + 1) % listLen]);
    }

    private void reverse(int pos, int length) {
        int back = (pos + length-1) % listLen;
        for (int i = 0; i < length / 2; i++) {
            int tmp = g[pos];
            g[pos] = g[back];
            g[back] = tmp;
            pos = (pos + 1) % listLen;
            back = (back + listLen - 1) % listLen;
        }
    }
}
