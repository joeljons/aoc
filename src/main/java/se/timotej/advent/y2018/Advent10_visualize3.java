package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Advent10_visualize3 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent10_visualize3().calc(Online.get(42));
        //System.out.println("svar = " + svar);
        // Online.submit(svar);
    }


    private int calc(List<String> strs) throws IOException {
        int svar = 0;
        List<Point> points = new ArrayList<>();
        Random r = new Random();
        for (int y = 0; y < strs.size(); y++) {
            String s = strs.get(y);
            for (int x = 0; x < s.length(); x++) {
                char c = s.charAt(x);
                if (c == '#') {
                    int dx, dy;
                    do {
                        dx = r.nextInt(11) - 6;
                        dy = r.nextInt(11) - 6;
                    } while (dx == 0 || dy == 0);
                    points.add(new Point(x + 700, y + 300, dx, dy));
                }
            }

        }

        while (innanfor(points)) {
            for (Point point : points) {
                point.x -= point.dx;
                point.y -= point.dy;
            }
        }
        Collections.shuffle(points);
        for (Point point : points) {
            point.x += point.dx;
            point.y += point.dy;
            System.out.println(point);
        }
        return svar;
    }

    private boolean innanfor(List<Point> points) {
        return IntStream.concat(
                points.stream().mapToInt(Point::getX),
                points.stream().mapToInt(Point::getY))
                .map(Math::abs)
                .max()
                .getAsInt() < 100000;
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

        @Override
        public String toString() {
            return String.format("position=<%6d, %6d> velocity=<%2d, %2d>", x, y, dx, dy);
        }
    }

}
