package se.timotej.advent.y2023;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent8b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent8b().calc(Online.get(8));
        System.out.println("svar = " + svar);
        //Online.submit(svar);
    }

    Map<String, Integer> mapping = new HashMap<>();

    int getMapping(String str) {
        return mapping.computeIfAbsent(str, s -> mapping.size());
    }

    private long calc(List<String> strs) {
        List<Integer> now = new ArrayList<>();
        int[][] g = new int[1000][2];
        boolean[] zs = new boolean[1000];
        List<Map<Pair<Integer, Integer>, Long>> loops = new ArrayList<>();
        int[] dirs = new int[strs.get(0).length()];
        for (int i = 0; i < dirs.length; i++) {
            dirs[i] = strs.get(0).charAt(i) == 'L' ? 0 : 1;
        }
        for (String str : strs.subList(2, strs.size())) {
            String[] split = str.split("[^0-9A-Z]+");
            g[getMapping(split[0])][0] = getMapping(split[1]);
            g[getMapping(split[0])][1] = getMapping(split[2]);
            if (split[0].endsWith("A")) {
                now.add(getMapping(split[0]));
            }
            if (split[0].endsWith("Z")) {
                zs[getMapping(split[0])] = true;
            }
            loops.add(new HashMap<>());
        }
        int dirIndex = 0;
        int[] nows = now.stream().mapToInt(Integer::valueOf).toArray();
        int founds = 0;
        long[] loopStart = new long[nows.length];
        long[] loopEnd = new long[nows.length];
        long[] zAt = new long[nows.length];
        long count = 0;
        do {
            for (int i = 0; i < nows.length; i++) {
                Pair<Integer, Integer> key = Pair.of(dirIndex, nows[i]);
                if (loops.get(i) != null && loops.get(i).containsKey(key)) {
//                    System.out.println("Loop for " + i + " " + loops.get(i).get(key) + " -> " + count);
                    loopStart[i] = loops.get(i).get(key);
                    loopEnd[i] = count;
                    loops.set(i, null);
                    founds++;
                }
                if (loops.get(i) != null) {
                    loops.get(i).put(key, count);
                }
                if (zs[nows[i]]) {
                    zAt[i] = count;
//                    System.out.println("zAt["+i+"] = " + zAt[i]);
                }

                nows[i] = g[nows[i]][dirs[dirIndex]];
            }
            count++;
            dirIndex = (dirIndex + 1) % dirs.length;

        } while (founds<nows.length);
//        System.out.println("Arrays.toString(loopStart) = " + Arrays.toString(loopStart));
//        System.out.println("Arrays.toString(loopEnd) = " + Arrays.toString(loopEnd));
//        System.out.println("Arrays.toString(zAt) = " + Arrays.toString(zAt));
        long jumps = loopEnd[0] - loopStart[0];
        for(int i=1;i<nows.length;i++){
            jumps = jumps / gcd(jumps, loopEnd[i] - loopStart[i]) * (loopEnd[i] - loopStart[i]);
        }
        return jumps;
    }
    private long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
