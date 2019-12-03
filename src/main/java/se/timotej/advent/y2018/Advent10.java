package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static se.timotej.advent.y2018.Util.findAllInts;

public class Advent10 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent10().calc(Online.get());
        //System.out.println("svar = " + svar);
        // Online.submit(svar);
    }


    private int calc(List<String> strs) {
        int svar = 0;
        List<Point> points = new ArrayList<>();
        for (String str : strs) {
            List<Integer> line = findAllInts(str);
            points.add(new Point(line.get(0),
                    line.get(1),
                    line.get(2),
                    line.get(3)));

        }
        long lastSize = Long.MAX_VALUE;
        for (int i = 0; ; i++) {
            int minx = points.stream().mapToInt(Point::getX).min().getAsInt();
            int maxx = points.stream().mapToInt(Point::getX).max().getAsInt();
            int miny = points.stream().mapToInt(Point::getY).min().getAsInt();
            int maxy = points.stream().mapToInt(Point::getY).max().getAsInt();
            long size = (maxy - miny) * (long) (maxx - minx);
            if (size > lastSize) {
                System.out.println("i = " + (i - 1));
                break;
            }
            lastSize = size;
            for (Point point : points) {
                point.x += point.dx;
                point.y += point.dy;
            }
        }
        for (Point point : points) {
            point.x -= point.dx;
            point.y -= point.dy;
        }
        int minx = points.stream().mapToInt(Point::getX).min().getAsInt();
        int maxx = points.stream().mapToInt(Point::getX).max().getAsInt();
        int miny = points.stream().mapToInt(Point::getY).min().getAsInt();
        int maxy = points.stream().mapToInt(Point::getY).max().getAsInt();
        boolean[][] g = new boolean[maxy - miny + 1][maxx - minx + 1];
        for (Point point : points) {
            g[point.y - miny][point.x - minx] = true;
        }
        for (boolean[] booleans : g) {
            for (boolean aBoolean : booleans) {
                System.out.print(aBoolean ? "#" : " ");
            }
            System.out.println();
        }

        return svar;
    }

    class Point {
        int x, y;
        int dx, dy;

        public Point(int x, int y, int dx, int dy) {
            this.x = x;
            this.y = y;
            this.dx = dx;
            this.dy = dy;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

}
