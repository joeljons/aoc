package se.timotej.advent.y2016;

import java.io.IOException;
import java.util.List;

public class Advent2b {

    public static void main(String[] args) throws IOException {
        String svar = new Advent2b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};
    String dirs = "URDL";
    String[] siffs = new String[]{
            "  1  ",
            " 234 ",
            "56789",
            " ABC ",
            "  D  "
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
                if(nyy>=0 && nyy<5 && nyx>=0 && nyx<5 && siffs[nyy].charAt(nyx) != ' '){
                    y=nyy;
                    x=nyx;
                }
            }
            svar.append(siffs[y].charAt(x));
        }
        return svar.toString();
    }
}