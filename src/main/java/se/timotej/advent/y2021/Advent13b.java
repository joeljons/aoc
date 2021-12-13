package se.timotej.advent.y2021;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Advent13b {

    public static void main(String[] args) throws IOException {
        new Advent13b().calc(Online.get(13));
    }

    private void calc(List<String> strs) {
        List<Point> points = new ArrayList<>();
        int i;
        for (i = 0; i < strs.size(); i++) {
            String str = strs.get(i);
            List<Integer> line = Util.findAllInts(str);
            if (line.size() == 0) {
                break;
            }
            int x = line.get(0);
            int y = line.get(1);
            points.add(new Point(x, y));

        }
        for (i = i + 1; i < strs.size(); i++) {
            String str = strs.get(i);
            List<Integer> line = Util.findAllInts(str);
            if (str.contains("x=")) {
                foldX(points, line.get(0));
            } else {
                foldY(points, line.get(0));
            }
        }

        Set<Point> g = new HashSet<>();
        int maxx = 0;
        int maxy = 0;
        for (Point point : points) {
            g.add(point);
            maxx = Math.max(point.x, maxx);
            maxy = Math.max(point.y, maxy);
        }
        for (int y = 0; y <= maxy; y++) {
            for (int x = 0; x <= maxx; x++) {
                if (g.contains(new Point(x, y))) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }

    private void foldY(List<Point> points, int foldLine) {
        for (Point point : points) {
            if (point.y > foldLine) {
                int dist = point.y - foldLine;
                point.y = foldLine - dist;
            }
        }
    }

    private void foldX(List<Point> points, int foldLine) {
        for (Point point : points) {
            if (point.x > foldLine) {
                int dist = point.x - foldLine;
                point.x = foldLine - dist;
            }
        }
    }

    private static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

}
