package se.timotej.advent.y2016;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Advent1b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent1b().calc(Online.get().get(0).split("[, ]+"));
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};

    private int calc(String[] strs) {
        int x = 0;
        int y = 0;
        Set<Pair<Integer, Integer>> seen = new HashSet<>();
        int dir = 0;
        for (String str : strs) {
            if (str.charAt(0) == 'R') {
                dir = (dir + 1) % 4;
            } else {
                dir = (dir + 3) % 4;
            }
            int dist = Integer.parseInt(str.substring(1));
            for(int i=0;i<dist;i++) {
                if(!seen.add(Pair.of(x,y))){
                    return Math.abs(x) + Math.abs(y);
                }
                x += dx[dir];
                y += dy[dir];
            }
        }
        return 0;
    }
}