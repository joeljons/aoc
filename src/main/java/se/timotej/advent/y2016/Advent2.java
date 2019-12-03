package se.timotej.advent.y2016;

import java.io.IOException;
import java.util.List;

public class Advent2 {

    public static void main(String[] args) throws IOException {
        String svar = new Advent2().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};
    String dirs = "URDL";
    int[][] siffs = new int[][]{
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
    };

    private String calc(List<String> strs) {
        int x = 1;
        int y = 1;
        StringBuilder svar = new StringBuilder();
        for (String str : strs) {
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                int dir = dirs.indexOf(c);
                int nyy = y+dy[dir];
                int nyx = x+dx[dir];
                if(nyy>=0 && nyy<3 && nyx>=0 && nyx<3){
                    y=nyy;
                    x=nyx;
                }
            }
            svar.append(siffs[y][x]);
        }
        return svar.toString();
    }
}