package se.timotej.advent.y2017;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Advent10b {
    public static void main(String[] args) throws IOException {
//        new Advent10().calc(5, singletonList("3,4,1,5"));
        new Advent10b().calc(256, Collections.singletonList(""));
        new Advent10b().calc(256, Collections.singletonList("AoC 2017"));
        new Advent10b().calc(256, Collections.singletonList("1,2,3"));
        new Advent10b().calc(256, Collections.singletonList("1,2,4"));
        new Advent10b().calc(256, Online.get(10));
    }

    int g[];
    int listLen;

    public String calc(int listLen, List<String> strs) {
        this.listLen = listLen;
        g = new int[listLen];
        for (int i = 0; i < listLen; i++) {
            g[i] = i;
        }
        String str = strs.get(0);
        int[] lengths = new int[str.length() + 5];
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            lengths[i] = c;
        }
        lengths[lengths.length - 5] = 17;
        lengths[lengths.length - 4] = 31;
        lengths[lengths.length - 3] = 73;
        lengths[lengths.length - 2] = 47;
        lengths[lengths.length - 1] = 23;


        int pos = 0;
        int skip = 0;
        for (int it = 0; it < 64; it++) {
            for (int length : lengths) {
                reverse(pos, length);
                pos += length + skip;
                pos %= listLen;
                skip++;
            }
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<16;i++){
            int sum = g[i*16];
            for (int j = 1; j <16; j++) {
                 sum ^=  g[i*16+j];
            }
            String s = Integer.toHexString(sum);
            if (s.length() < 2) {
                System.out.print("0");
                sb.append("0");
            }
            sb.append(s);
            System.out.print(s);
        }
        System.out.println();
        return sb.toString();
    }

    private void reverse(int pos, int length) {
        int back = (pos + length - 1) % listLen;
        for (int i = 0; i < length / 2; i++) {
            int tmp = g[pos];
            g[pos] = g[back];
            g[back] = tmp;
            pos = (pos + 1) % listLen;
            back = (back + listLen - 1) % listLen;
        }
    }
}
