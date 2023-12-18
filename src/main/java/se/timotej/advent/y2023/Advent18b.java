package se.timotej.advent.y2023;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.*;

public class Advent18b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent18b().calc(Online.get());
        System.out.println("svar = " + svar);
         Online.submit(svar);
    }

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};

    private long calc(List<String> strs) {
        int x = 0;
        int y = 0;
        SortedSet<Integer> values = new TreeSet<>();
        for (String str : strs) {
            String[] split = str.split(" ");
            int dir;
            char dirStr = split[2].charAt(7);
            if (dirStr == '3') {
                dir = 0;
            } else if (dirStr == '0') {
                dir = 1;
            } else if (dirStr == '1') {
                dir = 2;
            } else if (dirStr == '2') {
                dir = 3;
            } else {
                throw new RuntimeException();
            }
            int len = Integer.parseInt(split[2].substring(2, 7), 16);
            values.add(x);
            values.add(y);
            x += dx[dir] * len;
            y += dy[dir] * len;
            values.add(x);
            values.add(y);
        }
        for (Integer i : new ArrayList<>(values)) {
            values.add(i + 1);
        }
        Map<Integer, Integer> valueMap = new HashMap<>();
        Map<Integer, Integer> valueMapBack = new HashMap<>();
        {
            int i = 0;
            for (Integer value : values) {
                valueMap.put(value, i);
                valueMapBack.put(i, value);
                i++;
            }
        }

        Set<Pair<Integer, Integer>> g = new HashSet<>();
        x = 0;
        y = 0;
        for (String str : strs) {
            String[] split = str.split(" ");
            int dir;
            char dirStr = split[2].charAt(7);
            if (dirStr == '3') {
                dir = 0;
            } else if (dirStr == '0') {
                dir = 1;
            } else if (dirStr == '1') {
                dir = 2;
            } else if (dirStr == '2') {
                dir = 3;
            } else {
                throw new RuntimeException();
            }
            int len = Integer.parseInt(split[2].substring(2, 7), 16);
            int xValue = valueMap.get(x);
            int yValue = valueMap.get(y);
            int destX = x + dx[dir] * len;
            int destY = y + dy[dir] * len;
            while (x != destX || y != destY) {
                g.add(Pair.of(xValue, yValue));
                xValue += dx[dir];
                yValue += dy[dir];
                x = valueMapBack.get(xValue);
                y = valueMapBack.get(yValue);
            }
            g.add(Pair.of(xValue, yValue));
        }
        Queue<Pair<Integer, Integer>> q = new ArrayDeque<>();
        Pair<Integer, Integer> start = Pair.of(valueMap.get(1), valueMap.get(1));
        q.add(start);
        while (!q.isEmpty()) {
            Pair<Integer, Integer> now = q.remove();
            x = now.getLeft();
            y = now.getRight();
            for (int dir = 0; dir < 4; dir++) {
                Pair<Integer, Integer> key = Pair.of(x + dx[dir], y + dy[dir]);
                if (!g.contains(key)) {
                    g.add(key);
                    q.add(key);
                }
            }
        }
        long svar=0;
        for (Pair<Integer, Integer> xy : g) {
            x = xy.getLeft();
            y = xy.getRight();
            long xSize = valueMapBack.get(x + 1) - valueMapBack.get(x);
            long ySize = valueMapBack.get(y + 1) - valueMapBack.get(y);
            svar += xSize * ySize;
        }
        return svar;
    }
}
