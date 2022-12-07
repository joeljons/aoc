package se.timotej.advent.y2022;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent7 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent7().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    static long svar = 0;

    private long calc(List<String> strs) {
        Dir root = new Dir(null);
        Dir current = root;
        for (int i = 0; i < strs.size(); i++) {
            String line = strs.get(i);
            if (line.equals("$ cd /")) {
                current = root;
            } else if (line.equals("$ cd ..")) {
                current = current.parent;
            } else if (line.startsWith("$ cd")) {
                current = current.subdirs.get(line.substring(5));
            } else if (line.equals("$ ls")) {
                while (i +1< strs.size() && !strs.get(i + 1).startsWith("$")) {
                    i++;
                    line = strs.get(i);
                    if (line.startsWith("dir")) {
                        current.subdirs.put(line.substring(4), new Dir(current));
                    } else {
                        current.myFileSizes += Util.findAllLongs(line).get(0);
                    }
                }
            } else {
                throw new RuntimeException(line);
            }
        }

        root.calcSizes();

        return svar;
    }

    private static class Dir {
        long myFileSizes = 0;
        long recursiveSize = -1;
        Map<String, Dir> subdirs = new HashMap<>();
        Dir parent;

        public Dir(Dir parent) {
            this.parent = parent;
        }

        public long calcSizes() {
            recursiveSize = myFileSizes;
            for (Dir value : subdirs.values()) {
                recursiveSize += value.calcSizes();
            }
            if(recursiveSize <= 100000)svar += recursiveSize;
            return recursiveSize;
        }
    }
}
