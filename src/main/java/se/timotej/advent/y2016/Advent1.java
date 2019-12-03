package se.timotej.advent.y2016;

import java.io.IOException;

public class Advent1 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent1().calc(Online.get().get(0).split("[, ]+"));
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};

    private int calc(String[] strs) {
        int x = 0;
        int y = 0;
        int dir = 0;
        for (String str : strs) {
            if (str.charAt(0) == 'R') {
                dir = (dir + 1) % 4;
            } else {
                dir = (dir + 3) % 4;
            }
            int dist = Integer.parseInt(str.substring(1));
            x += dx[dir] * dist;
            y += dy[dir] * dist;
        }
        return Math.abs(x) + Math.abs(y);
    }
}