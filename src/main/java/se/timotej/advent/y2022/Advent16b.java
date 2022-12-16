package se.timotej.advent.y2022;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Advent16b {
    public static void main(String[] args) throws IOException {
        int svar = new Advent16b().calc(Online.get(16));
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        Map<Room, Valve> valves = new HashMap<>();
        for (String str : strs) {
            int flowRate = Util.findAllPositiveInts(str).get(0);
            String[] split = str.split(" ");
            String name = split[1];
            List<Room> tunnels = new ArrayList<>();
            for (int i = 9; i < split.length; i++) {
                tunnels.add(Room.valueOf(split[i].replace(",", "")));
            }
            valves.put(Room.valueOf(name), new Valve(name, flowRate, tunnels));
        }
        Map<State, Integer> now = new HashMap<>();
        now.put(new State(Room.AA, Room.AA, EnumSet.noneOf(Room.class), 0), 0);
        for (int minute = 0; minute < 26; minute++) {
            Map<State, Integer> next = new HashMap<>();
            for (Map.Entry<State, Integer> stateIntegerEntry : now.entrySet()) {
                State nowState = stateIntegerEntry.getKey();
                int nowScore = stateIntegerEntry.getValue();
                int myNextScore = nowScore + nowState.flowRate;
                Valve valve = valves.get(nowState.position);
                Valve valve2 = valves.get(nowState.position2);
                List<State> afterMyAction = new ArrayList<>();
                if (valve.flowRate > 0 && !nowState.released.contains(nowState.position)) {
                    EnumSet<Room> nextReleased = EnumSet.copyOf(nowState.released);
                    nextReleased.add(nowState.position);
                    afterMyAction.add(new State(nowState.position, nowState.position2, nextReleased, nowState.flowRate + valve.flowRate));
                }
                for (Room tunnel : valves.get(nowState.position).tunnels) {
                    afterMyAction.add(new State(tunnel, nowState.position2, nowState.released, nowState.flowRate));
                }
                for (State halfState : afterMyAction) {
                    if (valve2.flowRate > 0 && !halfState.released.contains(halfState.position2)) {
                        EnumSet<Room> nextReleased = EnumSet.copyOf(halfState.released);
                        nextReleased.add(halfState.position2);
                        State nextState = new State(halfState.position, halfState.position2, nextReleased, halfState.flowRate + valve2.flowRate);
                        maybeAddState(next, myNextScore, nextState);
                    }
                    for (Room tunnel : valves.get(halfState.position2).tunnels) {
                        State nextState = new State(halfState.position, tunnel, halfState.released, halfState.flowRate);
                        if (tunnel.compareTo(halfState.position) < 0) {
                            nextState = new State(tunnel, halfState.position, halfState.released, halfState.flowRate);
                        }
                        maybeAddState(next, myNextScore, nextState);
                    }
                }
            }
            now = next;
        }
        return now.values().stream().max(Integer::compareTo).get();
    }

    private static void maybeAddState(Map<State, Integer> next, int myNextScore, State nextState) {
        Integer existingScore = next.get(nextState);
        if (existingScore == null || existingScore < myNextScore) {
            next.put(nextState, myNextScore);
        }
    }

    private static class Valve {
        String name;
        int flowRate;
        List<Room> tunnels;

        public Valve(String name, int flowRate, List<Room> tunnels) {
            this.name = name;
            this.flowRate = flowRate;
            this.tunnels = tunnels;
        }
    }

    private static class State {
        Room position;

        Room position2;
        EnumSet<Room> released;

        int flowRate;

        public State(Room position, Room position2, EnumSet<Room> released, int flowRate) {
            this.position = position;
            this.position2 = position2;
            this.released = released;
            this.flowRate = flowRate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            State state = (State) o;
            return Objects.equals(position, state.position) && Objects.equals(position2, state.position2) && Objects.equals(released, state.released);
        }

        @Override
        public int hashCode() {
            return Objects.hash(position, position2, released);
        }
    }

    private enum Room {
        TM, ST, IX, OG, FR, HU, WC, JT, DW, RG, JQ, XX, IN, LR, TK, VM, IC, CH, OV, KQ, NR, MN, YY, OK, DK, CY, PR, DE, TJ, NS, PE, DU, DX, EQ, AA, WB, PF, FJ, GP, FK, DO, XO, PS, MD, EZ, GS, XU, WU, YW, HZ, TY, BP, EL, UX, FA, NN, LM
    }
}
