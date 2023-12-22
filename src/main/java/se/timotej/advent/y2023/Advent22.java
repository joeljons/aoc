package se.timotej.advent.y2023;

import java.io.IOException;
import java.util.*;

public class Advent22 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent22().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    Set<Point> universe = new HashSet<>();

    private long calc(List<String> strs) {
        long svar = 0;
        List<Brick> bricks = new ArrayList<>();
        for (String str : strs) {
            List<Integer> ints = Util.findAllInts(str);
            Brick brick = new Brick();
            for (int x = ints.get(0); x <= ints.get(3); x++) {
                for (int y = ints.get(1); y <= ints.get(4); y++) {
                    for (int z = ints.get(2); z <= ints.get(5); z++) {
                        Point point = new Point(x, y, z);
                        brick.cubes.add(point);
                        universe.add(point);
                    }
                }
            }
            bricks.add(brick);
        }

        boolean fell;
        do {
            fell = false;
            for (Brick brick : bricks) {
                if (canFAll(brick)) {
                    fell = true;
                    for (Point cube : brick.cubes) {
                        universe.remove(cube);
                        cube.z--;
                    }
                    universe.addAll(brick.cubes);
                }
            }
        } while (fell);

        for (Brick brick : bricks) {
            fell = false;
            for (Point cube : brick.cubes) {
                universe.remove(cube);
            }
            for (Brick brick2 : bricks) {
                if (brick == brick2) {
                    continue;
                }
                if (canFAll(brick2)) {
                    fell = true;
                    break;
                }
            }
            universe.addAll(brick.cubes);
            if (!fell) {
                svar++;
            }
        }

        return svar;
    }

    private boolean canFAll(Brick brick) {
        for (Point cube : brick.cubes) {
            if (cube.z == 1) {
                return false;
            }
            Point lower = new Point(cube.x, cube.y, cube.z - 1);
            if (!brick.cubes.contains(lower) && universe.contains(lower)) {
                return false;
            }
        }
        return true;
    }

    private static class Brick {
        List<Point> cubes = new ArrayList<>();

        @Override
        public String toString() {
            return "Brick{" +
                    "cubes=" + cubes +
                    '}';
        }
    }

    private static class Point {
        int x, y, z;

        public Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
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
            return x == point.x && y == point.y && z == point.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    '}';
        }
    }

}
