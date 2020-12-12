package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.List;

public class Advent12b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent12b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int x = 0;
        int y = 0;
        int wx=10;
        int wy=-1;
        for (String str : strs) {
            char c = str.charAt(0);
            int len = Integer.parseInt(str.substring(1));
            if (c == 'F') {
                x += wx * len;
                y += wy * len;
            } else if (c == 'R') {
                for (int i = 0; i < len / 90; i++) {
                    int tmp = wx;
                    wx = -wy;
                    wy = tmp;
                }
            } else if (c == 'L') {
                for (int i = 0; i < len / 90; i++) {
                    int tmp = wx;
                    wx = wy;
                    wy = -tmp;
                }
            } else if (c == 'N') {
                wy -= len;
            } else if (c == 'S') {
                wy += len;
            } else if (c == 'W') {
                wx -= len;
            } else if (c == 'E') {
                wx += len;
            }
        }
        return Math.abs(x) + Math.abs(y);
    }
}
