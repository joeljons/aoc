package se.timotej.advent.y2022;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Advent19 {
    public static void main(String[] args) throws IOException {
        int svar = new Advent19().calc(Online.get(19, ""));
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int svar = 0;
        for (String str : strs) {
            List<Integer> ints = Util.findAllPositiveInts(str);
            Cost cost = new Cost(ints);
            int geods = geodsIn24(cost);
            int mySvar = cost.blueprint * geods;
            System.out.println(cost.blueprint + "\t" + mySvar);
            svar += mySvar;
        }
        return svar;
    }

    private int geodsIn24(Cost cost) {
        State start = new State(1, 0, 0, 0, 0, 0, 0, 0);
        Map<State, State> now = new TreeMap<>();
        now.put(start, start);
        for (int minute = 1; minute <= 23; minute++) {
            Map<State, State> next = new TreeMap<>();
            for (State state : now.values()) {
                if (cost.geodeRobotCostOres <= state.ores
                        && cost.geodeRobotCostObsidians <= state.obsidians) {
                    State newState = new State(
                            state.oreRobots,
                            state.clayRobots,
                            state.obsidianRobots,
                            state.geodeRobots + 1,
                            state.ores + state.oreRobots - cost.geodeRobotCostOres,
                            state.clays + state.clayRobots,
                            state.obsidians + state.obsidianRobots - cost.geodeRobotCostObsidians,
                            state.geodes + state.geodeRobots
                    );

                    next.merge(newState, newState, (a, b) -> a.ores > b.ores ? a : b);
                } else if (minute < 23) {
                    for (int buyOreRobots = 0;
                         buyOreRobots <= 1 &&
                                 buyOreRobots * cost.oreRobotCostOres <= state.ores;
                         buyOreRobots++) {
                        for (int buyClayRobots = 0;
                             buyOreRobots + buyClayRobots <= 1 &&
                                     buyOreRobots * cost.oreRobotCostOres + buyClayRobots * cost.clayRobotCostOres <= state.ores;
                             buyClayRobots++) {
                            for (int buyObsidianRobots = 0;
                                 buyOreRobots + buyClayRobots + buyObsidianRobots <= 1 &&
                                         buyOreRobots * cost.oreRobotCostOres + buyClayRobots * cost.clayRobotCostOres + buyObsidianRobots * cost.obsidianRobotCostOres <= state.ores
                                         && buyObsidianRobots * cost.obsidianRobotCostClays <= state.clays;
                                 buyObsidianRobots++
                            ) {
                                State newState = new State(
                                        state.oreRobots + buyOreRobots,
                                        state.clayRobots + buyClayRobots,
                                        state.obsidianRobots + buyObsidianRobots,
                                        state.geodeRobots,
                                        state.ores + state.oreRobots - (buyOreRobots * cost.oreRobotCostOres + buyClayRobots * cost.clayRobotCostOres + buyObsidianRobots * cost.obsidianRobotCostOres),
                                        state.clays + state.clayRobots - buyObsidianRobots * cost.obsidianRobotCostClays,
                                        state.obsidians + state.obsidianRobots,
                                        state.geodes + state.geodeRobots
                                );
                                if (newState.ores - state.oreRobots >= cost.oreRobotCostOres
                                        && newState.ores - state.oreRobots >= cost.clayRobotCostOres
                                        && newState.ores - state.oreRobots >= cost.obsidianRobotCostOres && newState.clays - state.clayRobots >= cost.obsidianRobotCostClays
                                        && newState.ores - state.oreRobots >= cost.geodeRobotCostOres && newState.obsidians - state.obsidianRobots >= cost.geodeRobotCostObsidians) {
                                    continue;
                                }

                                next.merge(newState, newState, (a, b) -> a.ores > b.ores ? a : b);
                            }
                        }
                    }
                } else {
                    State newState = new State(
                            state.oreRobots,
                            state.clayRobots,
                            state.obsidianRobots,
                            state.geodeRobots,
                            state.ores + state.oreRobots,
                            state.clays + state.clayRobots,
                            state.obsidians + state.obsidianRobots,
                            state.geodes + state.geodeRobots
                    );

                    next.merge(newState, newState, (a, b) -> a.ores > b.ores ? a : b);
                }
            }
            now = next;
        }
        int svar = 0;
        for (State state : now.values()) {
            int totGeods = state.geodes + state.geodeRobots;
            if (totGeods > svar) {
                svar = totGeods;
            }
        }
        return svar;
    }

    private static class State implements Comparable<State> {
        int oreRobots;
        int clayRobots;
        int obsidianRobots;
        int geodeRobots;
        int ores;
        int clays;
        int obsidians;
        int geodes;

        public State(int oreRobots, int clayRobots, int obsidianRobots, int geodeRobots, int ores, int clays, int obsidians, int geodes) {
            this.oreRobots = oreRobots;
            this.clayRobots = clayRobots;
            this.obsidianRobots = obsidianRobots;
            this.geodeRobots = geodeRobots;
            this.ores = ores;
            this.clays = clays;
            this.obsidians = obsidians;
            this.geodes = geodes;
        }

        @Override
        public String toString() {
            return "State{" +
                    "oreRobots=" + oreRobots +
                    ", clayRobots=" + clayRobots +
                    ", obsidianRobots=" + obsidianRobots +
                    ", geodeRobots=" + geodeRobots +
                    ", ores=" + ores +
                    ", clays=" + clays +
                    ", obsidians=" + obsidians +
                    ", geodes=" + geodes +
                    '}';
        }

        @Override
        public int compareTo(State o) {
            if (oreRobots != o.oreRobots) {
                return oreRobots - o.oreRobots;
            }
            if (clayRobots != o.clayRobots) {
                return clayRobots - o.clayRobots;
            }
            if (obsidianRobots != o.obsidianRobots) {
                return obsidianRobots - o.obsidianRobots;
            }
            if (geodeRobots != o.geodeRobots) {
                return geodeRobots - o.geodeRobots;
            }
            if (clays != o.clays) {
                return clays - o.clays;
            }
            if (obsidians != o.obsidians) {
                return obsidians - o.obsidians;
            }
            return geodes - o.geodes;
        }
    }

    private static class Cost {
        int blueprint;
        int oreRobotCostOres;
        int clayRobotCostOres;
        int obsidianRobotCostOres;
        int obsidianRobotCostClays;
        int geodeRobotCostOres;
        int geodeRobotCostObsidians;

        public Cost(List<Integer> ints) {
            blueprint = ints.get(0);
            oreRobotCostOres = ints.get(1);
            clayRobotCostOres = ints.get(2);
            obsidianRobotCostOres = ints.get(3);
            obsidianRobotCostClays = ints.get(4);
            geodeRobotCostOres = ints.get(5);
            geodeRobotCostObsidians = ints.get(6);
        }
    }
}
