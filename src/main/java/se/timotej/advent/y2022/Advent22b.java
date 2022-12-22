package se.timotej.advent.y2022;

import org.apache.commons.lang3.tuple.Triple;

import java.io.IOException;
import java.util.List;

public class Advent22b {
    public static void main(String[] args) throws IOException {
        int svar = new Advent22b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{1, 0, -1, 0};
    int[] dy = new int[]{0, 1, 0, -1};
    int maxx;
    int maxy;

    private int calc(List<String> strs) {
        String inst = strs.get(strs.size() - 1);
        maxy = strs.size() - 2;
        maxx = strs.get(0).length();
        int y = 0;
        int x = 0;
        int dir = 0;
        while (strs.get(0).charAt(x) == ' ') {
            x++;
        }
        int dist = 0;
        for (int i = 0; i < inst.length(); i++) {
            if (Character.isDigit(inst.charAt(i))) {
                dist = 10 * dist + (inst.charAt(i) - '0');
            } else {
                Triple<Integer, Integer, Integer> destination = walk(x, y, dir, dist, strs);
                x = destination.getLeft();
                y = destination.getMiddle();
                dir = destination.getRight();

                dist = 0;
                if (inst.charAt(i) == 'L') {
                    dir = (dir + 3) % 4;
                } else {
                    dir = (dir + 1) % 4;
                }
            }
        }
        Triple<Integer, Integer, Integer> destination = walk(x, y, dir, dist, strs);
        x = destination.getLeft();
        y = destination.getMiddle();
        dir = destination.getRight();
        return 1000 * (y + 1) + 4 * (x + 1) + dir;
    }

    private Triple<Integer, Integer, Integer> walk(int x, int y, int dir, int dist, List<String> strs) {
        Triple<Integer, Integer, Integer> now = Triple.of(x, y, dir);
        for (int j = 0; j < dist; j++) {
            now = walk(now.getLeft(), now.getMiddle(), now.getRight(), strs);
        }
        return now;
    }

    private Triple<Integer, Integer, Integer> walk(int x, int y, int dir, List<String> strs) {
        int cx = x / 50;
        int cy = y / 50;
        int dirdir = dir;
        int xx = (x + dx[dir] + maxx) % maxx;
        int yy = (y + dy[dir] + maxy) % maxy;
        int cxx = xx / 50;
        int cyy = yy / 50;
        if (cxx != cx || cyy != cy) {
            if (cx == 1 && cy == 0 && dir == 2) { //x 50-99, y 0-49
                xx = 0;
                yy = 149 - y;
                dirdir = 0;
                if (xx / 50 != 0 || yy / 50 != 2) {
                    throw new RuntimeException("Gah");
                }
            } else if (cx == 1 && cy == 0 && dir == 3) { //x 50-99, y 0-49
                xx = 0;
                yy = 150 + (x - 50);
                dirdir = 0;
                if (xx / 50 != 0 || yy / 50 != 3) {
                    throw new RuntimeException("Gah");
                }
            } else if (cx == 2 && cy == 0 && dir == 0) { //x 100-149, y 0-49
                xx = 99;
                yy = 149 - y;
                dirdir = 2;
                if (xx / 50 != 1 || yy / 50 != 2) {
                    throw new RuntimeException("Gah");
                }
            } else if (cx == 2 && cy == 0 && dir == 1) { //x 100-149, y 0-49
                xx = 99;
                yy = 50 + (x - 100);
                dirdir = 2;
                if (xx / 50 != 1 || yy / 50 != 1) {
                    throw new RuntimeException("Gah");
                }
            } else if (cx == 2 && cy == 0 && dir == 3) { //x 100-149, y 0-49
                xx = x - 100;
                yy = 199;
                if (xx / 50 != 0 || yy / 50 != 3) {
                    throw new RuntimeException("Gah");
                }
            } else if (cx == 1 && cy == 1 && dir == 0) { //x 50-99, y 50-99
                xx = 100 + (y - 50);
                yy = 49;
                dirdir = 3;
                if (xx / 50 != 2 || yy / 50 != 0) {
                    throw new RuntimeException("Gah");
                }
            } else if (cx == 1 && cy == 1 && dir == 2) { //x 50-99, y 50-99
                xx = y - 50;
                yy = 100;
                dirdir = 1;
                if (xx / 50 != 0 || yy / 50 != 2) {
                    throw new RuntimeException("Gah");
                }
            } else if (cx == 0 && cy == 2 && dir == 2) { //x 0-49, y 100-149
                xx = 50;
                yy = 149 - y;
                dirdir = 0;
                if (xx / 50 != 1 || yy / 50 != 0) {
                    throw new RuntimeException("Gah");
                }
            } else if (cx == 0 && cy == 2 && dir == 3) { //x 0-49, y 100-149
                xx = 50;
                yy = 50 + x;
                dirdir = 0;
                if (xx / 50 != 1 || yy / 50 != 1) {
                    throw new RuntimeException("Gah");
                }
            } else if (cx == 1 && cy == 2 && dir == 0) { //x 50-99, y 100-149
                xx = 149;
                yy = 149 - y;
                dirdir = 2;
                if (xx / 50 != 2 || yy / 50 != 0) {
                    throw new RuntimeException("Gah");
                }
            } else if (cx == 1 && cy == 2 && dir == 1) { //x 50-99, y 100-149
                xx = 49;
                yy = 150 + (x - 50);
                dirdir = 2;
                if (xx / 50 != 0 || yy / 50 != 3) {
                    throw new RuntimeException("Gah");
                }
            } else if (cx == 0 && cy == 3 && dir == 0) { //x 0-49, y 150-199
                xx = 50 + (y - 150);
                yy = 149;
                dirdir = 3;
                if (xx / 50 != 1 || yy / 50 != 2) {
                    throw new RuntimeException("Gah");
                }
            } else if (cx == 0 && cy == 3 && dir == 1) { //x 0-49, y 150-199
                xx = 100 + x;
                yy = 0;
                if (xx / 50 != 2 || yy / 50 != 0) {
                    throw new RuntimeException("Gah");
                }
            } else if (cx == 0 && cy == 3 && dir == 2) { //x 0-49, y 150-199
                xx = 50 + (y - 150);
                yy = 0;
                dirdir = 1;
                if (xx / 50 != 1 || yy / 50 != 0) {
                    throw new RuntimeException("Gah");
                }
            } else if (strs.get(yy).charAt(xx) == ' ') {
                throw new RuntimeException("Gah");
            }
        }
        if (strs.get(yy).charAt(xx) == '.') {
            return Triple.of(xx, yy, dirdir);
        } else {
            return Triple.of(x, y, dir);
        }
    }
}
