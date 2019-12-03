package se.timotej.advent.y2015;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class Advent22 {

    private static final int OPPONENT_HP = 71;
    private static final int OPPONENT_DAMANGE = 10;

    public static void main(String[] args) throws IOException {
        int svar = new Advent22().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    Queue<State> q = new ArrayDeque<>();
    int leastManaWon = Integer.MAX_VALUE;

    private int calc(List<String> strs) {
        State start = new State(50, 500, 0, OPPONENT_HP, 0, 0, 0);
        q.add(start);

        while (!q.isEmpty()) {
            State state = q.remove();
            if (state.mana >= 53) {
                State next = state.castMagicMissile();
                useNewState(next);
            }
            if (state.mana >= 73) {
                State next = state.castDrain();
                useNewState(next);
            }
            if (state.mana >= 113 && state.shieldTimer == 0) {
                State next = state.castShield();
                useNewState(next);
            }
            if (state.mana >= 173 && state.poisonTimer == 0) {
                State next = state.castPoison();
                useNewState(next);
            }
            if (state.mana >= 229 && state.rechargeTimer == 0) {
                State next = state.castRecharge();
                useNewState(next);
            }

        }

        return leastManaWon;
    }

    private void useNewState(State next) {
        if (next.won()) {
            leastManaWon = Math.min(leastManaWon, next.manaUsed);
        } else if (next.isAlive() && next.manaUsed < leastManaWon) {
            q.add(next);
        }
    }

    private class State {
        int myHP;
        int mana;
        int manaUsed;
        int opponentHP;
        int shieldTimer;
        int poisonTimer;
        int rechargeTimer;

        public State(int myHP, int mana, int manaUsed, int opponentHP, int shieldTimer, int poisonTimer,
                     int rechargeTimer) {
            this.myHP = myHP;
            this.mana = mana;
            this.manaUsed = manaUsed;
            this.opponentHP = opponentHP;
            this.shieldTimer = shieldTimer;
            this.poisonTimer = poisonTimer;
            this.rechargeTimer = rechargeTimer;
        }

        private State copy() {
            return new State(myHP, mana, manaUsed, opponentHP, shieldTimer, poisonTimer, rechargeTimer);
        }

        private void doOpponentTurn() {
            if (opponentHP <= 0) {
                return;
            }
            if (shieldTimer > 0) {
                myHP -= OPPONENT_DAMANGE - 7;
                shieldTimer--;
            } else {
                myHP -= OPPONENT_DAMANGE;
            }
            if (poisonTimer > 0) {
                opponentHP -= 3;
                poisonTimer--;
            }
            if (rechargeTimer > 0) {
                mana += 101;
                rechargeTimer--;
            }
        }

        private void afterMyTurn() {
            if (shieldTimer > 0) {
                shieldTimer--;
            }
            if (poisonTimer > 0) {
                opponentHP -= 3;
                poisonTimer--;
            }
            if (rechargeTimer > 0) {
                mana += 101;
                rechargeTimer--;
            }
            doOpponentTurn();
        }

        public State castMagicMissile() {
            State next = copy();
            next.opponentHP -= 4;
            next.useMana(53);
            next.afterMyTurn();
            return next;
        }

        public State castDrain() {
            State next = copy();
            next.opponentHP -= 2;
            next.myHP += 2;
            next.useMana(73);
            next.afterMyTurn();
            return next;
        }

        public State castShield() {
            State next = copy();
            next.shieldTimer = 6;
            next.useMana(113);
            next.afterMyTurn();
            return next;
        }

        public State castPoison() {
            State next = copy();
            next.poisonTimer = 6;
            next.useMana(173);
            next.afterMyTurn();
            return next;
        }

        public State castRecharge() {
            State next = copy();
            next.rechargeTimer = 5;
            next.useMana(229);
            next.afterMyTurn();
            return next;
        }

        private void useMana(int amount) {
            mana -= amount;
            manaUsed += amount;
        }

        public boolean won() {
            return myHP > 0 && opponentHP <= 0;
        }

        public boolean isAlive() {
            return myHP > 0;
        }
    }
}
