package se.timotej.advent.y2021;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Advent13 {

    public static void main(String[] args) throws IOException {
        var svar = new Advent13().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        List<Point> points = new ArrayList<>();
        for (String str : strs) {
            List<Integer> line = Util.findAllInts(str);
            if (line.size() == 0) {
                break;
            }
            int x = line.get(0);
            int y = line.get(1);
            points.add(new Point(x, y));

        }
        for (Point point : points) {
            if (point.x > 655) {
                int dist = point.x - 655;
                point.x = 655 - dist;
            }
        }
        Set<Point> g = new HashSet<>(points);
        return g.size();
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
