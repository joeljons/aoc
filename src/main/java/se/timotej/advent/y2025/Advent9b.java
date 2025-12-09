package se.timotej.advent.y2025;

import java.io.IOException;
import java.util.*;

public class Advent9b {

    int[] dx = new int[]{0, 1, 0, -1};
    int[] dy = new int[]{-1, 0, 1, 0};

    public static void main(String[] args) throws IOException {
        long svar = new Advent9b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        List<Tile> originalTiles = new ArrayList<>();
        for (String str : strs) {
            List<Integer> input = Util.findAllInts(str);
            originalTiles.add(new Tile(input.get(0), input.get(1)));
        }

        Set<Integer> allNumbers = new TreeSet<>();
        for (Tile tile : originalTiles) {
            allNumbers.add(tile.x()-1);
            allNumbers.add(tile.x());
            allNumbers.add(tile.x()+1);
            allNumbers.add(tile.y()-1);
            allNumbers.add(tile.y());
            allNumbers.add(tile.y()+1);
        }
        Map<Integer, Integer> numberMapping = new HashMap<>();
        int[] numBack = new int[allNumbers.size()];
        int numIndex = 0;
        for (Integer number : allNumbers) {
            numBack[numIndex] = number;
            numberMapping.put(number, numIndex++);
        }
        List<Tile> tiles = new ArrayList<>();
        for (Tile originalTile : originalTiles) {
            tiles.add(new Tile(numberMapping.get(originalTile.x()), numberMapping.get(originalTile.y())));
        }

        int size = numBack.length;
        int[][] g = new int[size][size];

        for (int i = 0; i < tiles.size(); i++) {
            Tile from = tiles.get(i);
            Tile to = tiles.get((i + 1) % tiles.size());
            do {
                g[from.y()][from.x()] = 1;
                from = from.moveTowards(to);
            } while (!from.equals(to));
        }
        Queue<Tile> q = new ArrayDeque<>();
        q.add(new Tile(0, 0));
        while (!q.isEmpty()) {
            Tile now = q.remove();
            for (int dir = 0; dir < 4; dir++) {
                Tile next = new Tile(now.x() + dx[dir], now.y() + dy[dir]);
                if (next.x() >= 0 && next.x() < size && next.y() >= 0 && next.y() < size && g[next.y()][next.x()] == 0) {
                    g[next.y()][next.x()] = 2;
                    q.add(next);
                }
            }
        }

        long svar = 0;
        for (int i = 0; i < tiles.size(); i++) {
            for (int j = i + 1; j < tiles.size(); j++) {
                long area = area(tiles.get(i), tiles.get(j), numBack);
                if (area > svar && filled(g, tiles.get(i), tiles.get(j))) {
                    svar = area;
                }
            }
        }
        return svar;
    }

    private boolean filled(int[][] g, Tile a, Tile b) {
        int fromX = Math.min(a.x(), b.x());
        int toX = Math.max(a.x(), b.x());
        int fromY = Math.min(a.y(), b.y());
        int toY = Math.max(a.y(), b.y());
        for (int y = fromY; y <= toY; y++) {
            for (int x = fromX; x <= toX; x++) {
                if (g[y][x] == 2) {
                    return false;
                }
            }
        }
        return true;
    }

    private long area(Tile a, Tile b, int[] numBack) {
        return (long) (Math.abs(numBack[a.x()] - numBack[b.x()]) + 1)
                * (Math.abs(numBack[a.y()] - numBack[b.y()]) + 1);
    }


    private record Tile(int x, int y) {
        Tile moveTowards(Tile target) {
            if (x == target.x()) {
                if (y < target.y()) {
                    return new Tile(x, y + 1);
                } else {
                    return new Tile(x, y - 1);
                }
            } else {
                if (x < target.x()) {
                    return new Tile(x + 1, y);
                } else {
                    return new Tile(x - 1, y);
                }
            }
        }
    }

}
