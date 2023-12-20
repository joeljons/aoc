package se.timotej.advent.y2023;

import org.apache.commons.lang3.tuple.Triple;

import java.io.IOException;
import java.util.*;

public class Advent20 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent20().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    Queue<Triple<Module, Module, Boolean>> q = new ArrayDeque<>();
    long lows = 0;
    long highs = 0;

    private long calc(List<String> strs) {
        Map<String, Module> g = new HashMap<>();
        for (String str : strs) {
            String[] split = str.split("[ \\->,]+");
            Module module;
            if (split[0].startsWith("%")) {
                module = new Module(split[0].substring(1), ModuleType.FLIP_FLOP);
            } else if (split[0].startsWith("&")) {
                module = new Module(split[0].substring(1), ModuleType.CONJUNCTION);
            } else {
                module = new Module(split[0], ModuleType.BROADCAST);
            }
            g.put(module.name, module);
        }

        for (String str : strs) {
            String[] split = str.split("[ \\->,]+");
            String name;
            if (split[0].startsWith("%")) {
                name = split[0].substring(1);
            } else if (split[0].startsWith("&")) {
                name = split[0].substring(1);
            } else {
                name = split[0];
            }
            for (int i = 1; i < split.length; i++) {
                String dest = split[i];
                if (!g.containsKey(dest)) {
                    g.put(dest, new Module(dest, ModuleType.RECEIVER));
                }
                g.get(name).destinations.add(g.get(dest));
                g.get(dest).inputs.add(g.get(name));
            }
        }
        for (Module value : g.values()) {
            value.init();
        }

        for (int i = 0; i < 1000; i++) {
            q.add(Triple.of(null, g.get("broadcaster"), false));
            lows++;
            while (!q.isEmpty()) {
                Triple<Module, Module, Boolean> now = q.remove();
//                System.out.printf("%s -%s-> %s%n", now.getLeft() == null ? "button" : now.getLeft().name, now.getRight() ? "high" : "low", now.getMiddle().name);
                now.getMiddle().send(now.getLeft(), now.getRight());
            }
        }
        return lows * highs;
    }

    private class Module {
        String name;
        ModuleType type;
        List<Module> destinations = new ArrayList<>();
        List<Module> inputs = new ArrayList<>();

        boolean flipFlopState;
        boolean[] conjunctionIn;

        public Module(String name, ModuleType type) {
            this.name = name;
            this.type = type;
        }

        public void init() {
            if (type == ModuleType.FLIP_FLOP) {
                flipFlopState = false;
            } else if (type == ModuleType.CONJUNCTION) {
                conjunctionIn = new boolean[inputs.size()];
            }
        }

        public void send(Module from, boolean pulse) {
            if (type == ModuleType.FLIP_FLOP) {
                if (!pulse) {
                    flipFlopState = !flipFlopState;
                    doSend(flipFlopState);
                }
            } else if (type == ModuleType.CONJUNCTION) {
                boolean allHigh = true;
                int flips = 0;
                for (int i = 0; i < conjunctionIn.length; i++) {
                    if (inputs.get(i) == from) {
                        conjunctionIn[i] = pulse;
                        flips++;
                    }
                    if (!conjunctionIn[i]) {
                        allHigh = false;
                    }
                }
                if (flips != 1) {
                    throw new RuntimeException();
                }
                doSend(!allHigh);
            } else if (type == ModuleType.BROADCAST) {
                doSend(pulse);
            }
        }

        private void doSend(boolean pulse) {
            for (Module destination : destinations) {
                if (pulse) {
                    highs++;
                } else {
                    lows++;
                }
                q.add(Triple.of(this, destination, pulse));
            }
        }

        @Override
        public String toString() {
            return "Module{" +
                    "name='" + name + '\'' +
                    ", type=" + type +
                    '}';
        }
    }

    enum ModuleType {FLIP_FLOP, CONJUNCTION, BROADCAST, RECEIVER}
}
