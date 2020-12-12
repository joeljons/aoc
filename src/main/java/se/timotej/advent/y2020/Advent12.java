package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.List;

public class Advent12 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent12().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{1, 0, -1, 0};
    int[] dy = new int[]{0, 1, 0, -1};

    private int calc(List<String> strs) {
        int dir = 0;
        int x = 0;
        int y = 0;
        for (String str : strs) {
            char c = str.charAt(0);
            int len = Integer.parseInt(str.substring(1));
            if (c == 'F') {
                x += dx[dir] * len;
                y += dy[dir] * len;
            } else if (c == 'R') {
                for (int i = 0; i < len / 90; i++) {
                    dir = (dir + 1) % 4;
                }
            } else if (c == 'L') {
                for (int i = 0; i < len / 90; i++) {
                    dir = (dir + 3) % 4;
                }
            } else if (c == 'N') {
                y -= len;
            } else if (c == 'S') {
                y += len;
            } else if (c == 'W') {
                x -= len;
            } else if (c == 'E') {
                x += len;
            }
        }
        return Math.abs(x) + Math.abs(y);
    }
}
