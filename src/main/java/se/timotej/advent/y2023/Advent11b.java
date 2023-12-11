package se.timotej.advent.y2023;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Advent11b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent11b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;
        List<Pair<Long, Long>> galaxies = new ArrayList<>();
        int maxx = strs.get(0).length();
        int maxy = strs.size();

        List<Integer> xAdds = new ArrayList<>();
        List<Integer> yAdds = new ArrayList<>();
        for (int y = 0; y < maxy; y++) {
            if (!strs.get(y).contains("#")) {
                yAdds.add(y);
            }
        }
        for (int x = 0; x < maxx; x++) {
            boolean add = true;
            for (int y = 0; y < maxy; y++) {
                if (strs.get(y).charAt(x) != '.') {
                    add = false;
                }
            }
            if (add) {
                xAdds.add(x);
            }
        }

        long yy = 0;
        for (int y = 0; y < maxy; y++) {
            if (yAdds.contains(y)) {
                yy += 1000000 - 1;
            }
            long xx = 0;
            for (int x = 0; x < maxx; x++) {
                if (xAdds.contains(x)) {
                    xx += 1000000 - 1;
                }
                if (strs.get(y).charAt(x) == '#') {
                    galaxies.add(Pair.of(x + xx, y + yy));
                }
            }
        }

        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                svar += Math.abs(galaxies.get(i).getLeft() - galaxies.get(j).getLeft()) + Math.abs(galaxies.get(i).getRight() - galaxies.get(j).getRight());
            }
        }

        return svar;
    }
}
