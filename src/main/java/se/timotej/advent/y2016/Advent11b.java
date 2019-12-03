package se.timotej.advent.y2016;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Advent11b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent11b().calc(Online.get(11));
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        byte[] startState = new byte[]{
                // generator, microchip
                1, 1, // thulium
                1, 2, // plutonium
                1, 2, // strontium
                3, 3, // promethium
                3, 3, // ruthenium
                1, 1, // elerium
                1, 1, // dilithium
                1 // me :)
        };
        Map<State, Integer> dist = new HashMap<>();
        State startStateObj = new State(startState);
        dist.put(startStateObj, 0);
        Queue<State> q = new ArrayDeque<>();
        q.add(startStateObj);
        while (!q.isEmpty()) {
            State nuState = q.remove();
            byte[] nu = nuState.b;
            Integer nuDist = dist.get(nuState);
//            System.out.println(nuDist +"\t"+Arrays.toString(nu));
            for (int i = 0; i < 14; i++) {
                if (nu[i] != nu[14]) {
                    continue;
                }
                for (int j = i + 1; j <= 14; j++) {
                    //if(j>=5 && j<14)continue;
                    if (j != 14 && nu[j] != nu[14]) {
                        continue;
                    }
                    for (int dy = -1; dy <= 1; dy+=2) {
                        byte[] next = Arrays.copyOf(nu, nu.length);
                        int nextFloor = dy + nu[14];
                        if (1 <= nextFloor && nextFloor <= 4) {
                            next[i] = (byte) nextFloor;
                            if (j != 14) {
                                next[j] = (byte) nextFloor;
                            }
                            next[14] = (byte) nextFloor;
                            if (isStateDone(next)) {
                                return nuDist + 1;
                            }
                            if (isStateOk(next)) {
                                State nextState = new State(next);
                                if (!dist.containsKey(nextState)) {
//                                    System.out.println("nextState = " + Arrays.toString(next));
                                    dist.put(nextState, nuDist + 1);
                                    q.add(nextState);
                                }
                            }
                        }
                    }
                }
            }
        }

        return 0;
    }

    private boolean isStateDone(byte[] next) {
        for (byte b : next) {
            if (b != 4) {
                return false;
            }
        }
        return true;
    }

    private boolean isStateOk(byte[] state) {
        for (int k = 0; k < 7; k++) {
            if (state[2 * k] != state[2 * k + 1]) {
                for (int m = 0; m < 7; m++) {
                    if (k != m && state[2 * k + 1] == state[2 * m]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private class State {
        byte[] b;

        public State(byte[] b) {
            this.b = b;
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
            return Arrays.equals(b, state.b);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(b);
        }
    }
}

