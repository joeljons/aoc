package se.timotej.advent.y2022;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Advent23 {
    public static void main(String[] args) throws IOException {
        int svar = new Advent23().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = {0, 0, -1, 1};
    int[] dy = {-1, 1, 0, 0};

    private int calc(List<String> strs) {
        Set<Pair<Integer, Integer>> now = new HashSet<>();
        for (int y = 0; y < strs.size(); y++) {
            for (int x = 0; x < strs.get(y).length(); x++) {
                if (strs.get(y).charAt(x) == '#') {
                    now.add(Pair.of(x, y));
                }
            }
        }
        for (int round = 0; round < 10; round++) {
            Map<Pair<Integer, Integer>, Integer> destinationCount = new HashMap<>();
            Map<Pair<Integer, Integer>, Pair<Integer, Integer>> destinationPos = new HashMap<>();
            for (Pair<Integer, Integer> elf : now) {
                int nearElfCount = 0;
                for (int xx = elf.getLeft() - 1; xx <= elf.getLeft() + 1; xx++) {
                    for (int yy = elf.getRight() - 1; yy <= elf.getRight() + 1; yy++) {
                        if (now.contains(Pair.of(xx, yy))) {
                            nearElfCount++;
                        }
                    }
                }
                Pair<Integer, Integer> destination = nearElfCount == 1 ? elf : null;
                for (int d = 0; d < 4 && destination == null; d++) {
                    int dir = (d + round) % 4;
                    int xx = elf.getLeft() + dx[dir];
                    int yy = elf.getRight() + dy[dir];
                    if (!now.contains(Pair.of(xx, yy))) {
                        if (dx[dir] == 0) {
                            if (!now.contains(Pair.of(xx - 1, yy)) && !now.contains(Pair.of(xx + 1, yy))) {
                                destination = Pair.of(xx, yy);
                            }
                        } else {
                            if (!now.contains(Pair.of(xx, yy - 1)) && !now.contains(Pair.of(xx, yy + 1))) {
                                destination = Pair.of(xx, yy);
                            }
                        }
                    }
                }
                if (destination == null) {
                    destination = elf;
                }
                destinationCount.merge(destination, 1, Integer::sum);
                destinationPos.put(elf, destination);
            }
            Set<Pair<Integer, Integer>> next = new HashSet<>();
            for (Pair<Integer, Integer> elf : now) {
                if (destinationCount.get(destinationPos.get(elf)) == 1) {
                    next.add(destinationPos.get(elf));
                } else {
                    next.add(elf);
                }
            }
            now = next;
        }
        int minx = Integer.MAX_VALUE;
        int maxx = Integer.MIN_VALUE;
        int miny = Integer.MAX_VALUE;
        int maxy = Integer.MIN_VALUE;
        for (Pair<Integer, Integer> elf : now) {
            int x = elf.getLeft();
            int y = elf.getRight();
            minx = Math.min(x, minx);
            maxx = Math.max(x, maxx);
            miny = Math.min(y, miny);
            maxy = Math.max(y, maxy);
        }
        return (maxx - minx + 1) * (maxy - miny + 1) - now.size();
    }
}
