package se.timotej.advent.y2025;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Advent9 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent9().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        List<Tile> tiles = new ArrayList<>();
        for (String str : strs) {
            List<Long> input = Util.findAllLongs(str);
            tiles.add(new Tile(input.get(0), input.get(1)));
        }
        long svar = 0;
        for (int i = 0; i < tiles.size(); i++) {
            for (int j = i + 1; j < tiles.size(); j++) {
                long area = area(tiles.get(i), tiles.get(j));
                if (area > svar) {
                    svar = area;
                }
            }
        }
        return svar;
    }


    private long area(Tile a, Tile b) {
        return Math.abs(a.x() - b.x() + 1) * Math.abs(a.y() - b.y() + 1);
    }

    private record Tile(long x, long y) {
    }

}
