package se.timotej.advent.y2022;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Advent16 {
    public static void main(String[] args) throws IOException {
        int svar = new Advent16().calc(Online.get());
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
        now.put(new State(Room.AA, EnumSet.noneOf(Room.class), 0), 0);
        for (int minute = 0; minute < 30; minute++) {
            Map<State, Integer> next = new HashMap<>();
            for (Map.Entry<State, Integer> stateIntegerEntry : now.entrySet()) {
                State nowState = stateIntegerEntry.getKey();
                int nowScore = stateIntegerEntry.getValue();
                Valve valve = valves.get(nowState.position);
                if (valve.flowRate > 0 && !nowState.released.contains(nowState.position)) {
                    EnumSet<Room> nextReleased = EnumSet.copyOf(nowState.released);
                    nextReleased.add(nowState.position);
                    State nextState = new State(nowState.position, nextReleased, nowState.flowRate + valve.flowRate);
                    int myNextScore = nowScore + nowState.flowRate;
                    Integer existingScore = next.get(nextState);
                    if (existingScore == null || existingScore < myNextScore) {
                        next.put(nextState, myNextScore);
                    }
                }
                for (Room tunnel : valves.get(nowState.position).tunnels) {
                    State nextState = new State(tunnel, nowState.released, nowState.flowRate);
                    int myNextScore = nowScore + nowState.flowRate;
                    Integer existingScore = next.get(nextState);
                    if (existingScore == null || existingScore < myNextScore) {
                        next.put(nextState, myNextScore);
                    }
                }
            }
            now = next;
        }
        return now.values().stream().max(Integer::compareTo).get();
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
        EnumSet<Room> released;

        int flowRate;

        public State(Room position, EnumSet<Room>  released, int flowRate) {
            this.position = position;
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
            return Objects.equals(position, state.position) && Objects.equals(released, state.released);
        }

        @Override
        public int hashCode() {
            return Objects.hash(position, released);
        }
    }

    private enum Room {
        TM, ST, IX, OG, FR, HU, WC, JT, DW, RG, JQ, XX, IN, LR, TK, VM, IC, CH, OV, KQ, NR, MN, YY, OK, DK, CY, PR, DE, TJ, NS, PE, DU, DX, EQ, AA, WB, PF, FJ, GP, FK, DO, XO, PS, MD, EZ, GS, XU, WU, YW, HZ, TY, BP, EL, UX, FA, NN, LM
    }
}
