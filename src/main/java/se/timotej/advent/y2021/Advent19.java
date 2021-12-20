package se.timotej.advent.y2021;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent19 {

    public static void main(String[] args) throws IOException {
        long start = System.nanoTime();
        var svar = new Advent19().calc(Online.get());
        long duration = System.nanoTime() - start;
        System.out.println("duration = " + duration / 1000000);
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        List<Scanner> scanners = new ArrayList<>();
        Scanner scanner = new Scanner();
        scanners.add(scanner);
        for (int i = 1; i < strs.size(); i++) {
            String str = strs.get(i);
            if (str.isEmpty()) {
                i++;
                scanner = new Scanner();
                scanners.add(scanner);
                continue;
            }
            List<Integer> allInts = Util.findAllInts(str);
            scanner.points.add(new Point(allInts.get(0), allInts.get(1), allInts.get(2)));
        }
        int nScan = scanners.size();
        boolean[] aligned = new boolean[nScan];
        aligned[0] = true;
        boolean allAligned;
        do {
            System.out.println("aligned = " + Arrays.toString(aligned));
            allAligned = true;
            for (int base = 0; base < nScan; base++) {
                if (!aligned[base]) {
                    allAligned = false;
                    continue;
                }
                for (int target = 0; target < nScan; target++) {
                    if (aligned[target]) {
                        continue;
                    }
                    if (tryAlign(scanners.get(base), scanners.get(target))) {
                        System.out.printf("Aligned %d -> %d%n", base, target);
                        aligned[target] = true;
                    }
                }
            }
        } while (!allAligned);

        Set<Point> allPoints = new HashSet<>();
        for (Scanner s : scanners) {
            allPoints.addAll(s.points);
        }
        return allPoints.size();
    }

    private boolean tryAlign(Scanner base, Scanner target) {
        Integer[] mapping = new Integer[]{0, 1, 2};
        do {
            int[] multiplier = new int[3];
            for (multiplier[0] = -1; multiplier[0] <= 1; multiplier[0] += 2) {
                for (multiplier[1] = -1; multiplier[1] <= 1; multiplier[1] += 2) {
                    for (multiplier[2] = -1; multiplier[2] <= 1; multiplier[2] += 2) {
                        for (int baseStart = 0; baseStart < base.points.size(); baseStart++) {
                            Point basePoint = base.points.get(baseStart);
                            for (int targetStart = 0; targetStart < target.points.size(); targetStart++) {
                                int matches = 0;
                                Point targetPoint = target.points.get(targetStart).modify(mapping, multiplier);
                                Point delta = basePoint.minus(targetPoint);
                                for (Point point : target.points) {
                                    Point flyttad = point.modify(mapping, multiplier).add(delta);
                                    if (base.points.contains(flyttad)) {
                                        matches++;
                                    }
                                }
                                if (matches >= 12) {
                                    System.out.println("delta = " + delta);
                                    System.out.println("matches = " + matches);
                                    List<Point> flyttade = new ArrayList<>();
                                    for (Point point : target.points) {
                                        Point flyttad = point.modify(mapping, multiplier).add(delta);
                                        flyttade.add(flyttad);
                                    }
                                    target.points = flyttade;
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        } while (Util.nextPermutation(mapping));
        return false;
    }

    private static class Scanner {
        List<Point> points = new ArrayList<>();
    }

    private static class Point {
        int[] g = new int[3];

        public Point(int x, int y, int z) {
            g[0] = x;
            g[1] = y;
            g[2] = z;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "g=" + Arrays.toString(g) +
                    '}';
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
            return Arrays.equals(g, point.g);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(g);
        }

        public Point modify(Integer[] mapping, int[] multiplier) {
            return new Point(g[mapping[0]] * multiplier[0],
                    g[mapping[1]] * multiplier[1],
                    g[mapping[2]] * multiplier[2]);
        }

        public Point minus(Point targetPoint) {
            return new Point(g[0] - targetPoint.g[0],
                    g[1] - targetPoint.g[1],
                    g[2] - targetPoint.g[2]);
        }

        public Point add(Point targetPoint) {
            return new Point(g[0] + targetPoint.g[0],
                    g[1] + targetPoint.g[1],
                    g[2] + targetPoint.g[2]);
        }
    }
}