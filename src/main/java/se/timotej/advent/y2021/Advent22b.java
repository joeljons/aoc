package se.timotej.advent.y2021;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class Advent22b {

    public static void main(String[] args) throws IOException {
        var svar = new Advent22b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long sum = 0;
        TreeSet<Long> xSeg = new TreeSet<>();
        TreeSet<Long> ySeg = new TreeSet<>();
        TreeSet<Long> zSeg = new TreeSet<>();
        for (String str : strs) {
            List<Long> allInts = Util.findAllLongs(str);
            long x0 = allInts.get(0);
            long x1 = allInts.get(1);
            long y0 = allInts.get(2);
            long y1 = allInts.get(3);
            long z0 = allInts.get(4);
            long z1 = allInts.get(5);
            xSeg.add(x0);
            xSeg.add(x0 + 1);
            xSeg.add(x1);
            xSeg.add(x1 + 1);
            ySeg.add(y0);
            ySeg.add(y0 + 1);
            ySeg.add(y1);
            ySeg.add(y1 + 1);
            zSeg.add(z0);
            zSeg.add(z0 + 1);
            zSeg.add(z1);
            zSeg.add(z1 + 1);
        }
        SegMapping xMapping = new SegMapping(xSeg);
        SegMapping yMapping = new SegMapping(ySeg);
        SegMapping zMapping = new SegMapping(zSeg);
        boolean[][][] taken = new boolean[xSeg.size()][ySeg.size()][zSeg.size()];
        for (String str : strs) {
            boolean turn = str.startsWith("on");
            List<Long> allInts = Util.findAllLongs(str);
            System.out.println("str = " + str);
            long x0 = allInts.get(0);
            long x1 = allInts.get(1);
            long y0 = allInts.get(2);
            long y1 = allInts.get(3);
            long z0 = allInts.get(4);
            long z1 = allInts.get(5);
            int fromXPos = xMapping.seg2Pos.get(x0);
            int toXPos = xMapping.seg2Pos.get(x1);
            int fromYPos = yMapping.seg2Pos.get(y0);
            int toYPos = yMapping.seg2Pos.get(y1);
            int fromZPos = zMapping.seg2Pos.get(z0);
            int toZPos = zMapping.seg2Pos.get(z1);
            for (int xPos = fromXPos; xPos <= toXPos; xPos++) {
                for (int yPos = fromYPos; yPos <= toYPos; yPos++) {
                    for (int zPos = fromZPos; zPos <= toZPos; zPos++) {
                        taken[xPos][yPos][zPos] = turn;
                    }
                }
            }
        }
        for (int xPos = 0; xPos < xSeg.size(); xPos++) {
            for (int yPos = 0; yPos < ySeg.size(); yPos++) {
                for (int zPos = 0; zPos < zSeg.size(); zPos++) {
                    if (taken[xPos][yPos][zPos]) {
                        sum += (xMapping.pos2Seg[xPos + 1] - xMapping.pos2Seg[xPos])
                                * (yMapping.pos2Seg[yPos + 1] - yMapping.pos2Seg[yPos])
                                * (zMapping.pos2Seg[zPos + 1] - zMapping.pos2Seg[zPos]);
                    }
                }
            }
        }
        return sum;
    }

    private static class SegMapping {
        Map<Long, Integer> seg2Pos = new HashMap<>();
        long[] pos2Seg;

        public SegMapping(TreeSet<Long> seg) {
            int pos = 0;
            pos2Seg = new long[seg.size()];
            for (Long value : seg) {
                seg2Pos.put(value, pos);
                pos2Seg[pos] = value;
                pos++;
            }
        }
    }

}
