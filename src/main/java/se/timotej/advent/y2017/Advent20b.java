package se.timotej.advent.y2017;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Advent20b {
    public static void main(String[] args) throws IOException {
        //new Advent20b().calc(Online.get(20, "_test"));
        new Advent20b().calc(Online.get(20));
    }

    private void calc(List<String> strs) {
        Set<Particle> g = new HashSet<>();
        for (String str : strs) {
            g.add(new Particle(str));
        }
        for (int it = 0; it < 10000; it++) {
            System.out.println("g.size() = " + g.size());
            Map<Particle, Particle> G = new TreeMap<>();
            Set<Particle> remove = new HashSet<>();
            for (Particle particle : g) {
                Particle existing = G.put(particle, particle);
                if (existing != null) {
                    remove.add(existing);
                    remove.add(particle);
                }
            }
            g.removeAll(remove);
            for (Particle particle : g) {
                particle.tick();
            }
        }
        Online.submit(20, 2, g.size());
    }

    private class Particle implements Comparable<Particle> {
        long px, py, pz;
        long vx, vy, vz;
        long ax, ay, az;

        Particle(String s) {
            Pattern pattern = Pattern.compile("p=<(-?\\d+),(-?\\d+),(-?\\d+)>, v=<(-?\\d+),(-?\\d+),(-?\\d+)>, a=<" +
                    "(-?\\d+),(-?\\d+),(-?\\d+)>");
            Matcher matcher = pattern.matcher(s);
            if (!matcher.matches()) {
                throw new RuntimeException("Invalid input: " + s);
            }
            px = Long.parseLong(matcher.group(1));
            py = Long.parseLong(matcher.group(2));
            pz = Long.parseLong(matcher.group(3));
            vx = Long.parseLong(matcher.group(4));
            vy = Long.parseLong(matcher.group(5));
            vz = Long.parseLong(matcher.group(6));
            ax = Long.parseLong(matcher.group(7));
            ay = Long.parseLong(matcher.group(8));
            az = Long.parseLong(matcher.group(9));
        }

        public void tick() {
            vx += ax;
            px += vx;
            vy += ay;
            py += vy;
            vz += az;
            pz += vz;
        }

        @Override
        public int compareTo(Particle o) {
            long cmp = px - o.px;
            if (cmp != 0) {
                return Long.signum(cmp);
            }
            cmp = py - o.py;
            if (cmp != 0) {
                return Long.signum(cmp);
            }
            cmp = pz - o.pz;
            return Long.signum(cmp);
        }
    }
}
