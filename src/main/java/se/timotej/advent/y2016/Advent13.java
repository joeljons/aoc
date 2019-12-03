package se.timotej.advent.y2016;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class Advent13 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent13().calc(Online.get(13).get(0));
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int favorite;

    private int calc(String str) {
        favorite = Integer.parseInt(str);
        //favorite=10;
        for(int y=0;y<=6;y++){
            for(int x=0;x<=9;x++){
                System.out.print(isOpen(x,y)?'.':"#");
            }
            System.out.println();
        }
        Set<Pair<Integer, Integer>> seen = new HashSet<>();
        Pair<Integer, Integer> startPos = Pair.of(1, 1);
        seen.add(startPos);
        Queue<Pair<Pair<Integer, Integer>, Integer>> q = new ArrayDeque<>();
        q.add(Pair.of(startPos, 0));
        while (!q.isEmpty()) {
            Pair<Pair<Integer, Integer>, Integer> nu = q.remove();
            System.out.println("nu = " + nu);
            int x = nu.getKey().getLeft();
            int y = nu.getKey().getRight();
            int dist = nu.getValue();
            for (int dy = -1; dy <= 1; dy++) {
                for (int dx = -1; dx <= 1; dx++) {
                    if ((dy == 0) == (dx == 0)) {
                        continue;
                    }
                    int ny = y + dy;
                    int nx = x + dx;
                    if (isOpen(nx, ny)) {
                        if (nx == 31 && ny == 39) {
//                        if (nx == 7 && ny == 4) {
                            return dist + 1;
                        }
                        Pair<Integer, Integer> pos = Pair.of(nx, ny);
                        if (!seen.contains(pos)) {
                            seen.add(pos);
                            q.add(Pair.of(pos, dist + 1));
                        }
                    }
                }
            }
        }
        return 0;
    }

    private boolean isOpen(int x, int y) {
        if (x < 0 || y < 0) {
            return false;
        }
        return Integer.bitCount(x * x + 3 * x + 2 * x * y + y + y * y + favorite) % 2 == 0;
    }

}