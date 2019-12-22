package se.timotej.advent.y2019;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Advent22b {
    private BigInteger CARD_COUNT = new BigInteger("119315717514047");
    private long CARD_COUNT_L = CARD_COUNT.longValue();

    private BigInteger oneBase;
    private BigInteger oneEach;
    private BigInteger oneInverse;

    private BigInteger manyBase;
    private BigInteger manyInverse;

    private long manyTimes;

    public static void main(String[] args) throws IOException {
        long start = System.nanoTime();
        String svar = new Advent22b().calc(Online.get(22));
        long duration = (System.nanoTime() - start) / 1000000;
        System.out.println("duration = " + duration);
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    List<Function<Long, Long>> actions;

    private String calc(List<String> strs) {
        actions = new ArrayList<>();
        for (String str : strs) {
            if (str.equals("deal into new stack")) {
                actions.add(cp -> CARD_COUNT_L - cp - 1);
            } else if (str.startsWith("cut")) {
                final long cut = Util.findAllLongs(str).get(0);
                actions.add(cp -> (cp + CARD_COUNT_L - cut) % CARD_COUNT_L);
            } else if (str.startsWith("deal with increment ")) {
                final long increment = Util.findAllLongs(str).get(0);
                actions.add(cp -> (increment * cp) % CARD_COUNT_L);
            } else {
                throw new RuntimeException(str);
            }
        }

        long timesLeft = 101741582076661L;
        for (manyTimes = 1; manyTimes * manyTimes * 2 < timesLeft; manyTimes *= 2) {
        }

        oneBase = BigInteger.valueOf(doOneRoundSlow(0));
        oneEach = BigInteger.valueOf((doOneRoundSlow(1) - oneBase.longValue() + CARD_COUNT_L) % CARD_COUNT_L);
        oneInverse = oneEach.modInverse(CARD_COUNT);

        manyBase = doManyRounds(BigInteger.ZERO);
        BigInteger manyEach = doManyRounds(BigInteger.ONE).subtract(manyBase).add(CARD_COUNT).mod(CARD_COUNT);
        manyInverse = manyEach.modInverse(CARD_COUNT);

        BigInteger cardPos = new BigInteger("2020");
        for (; timesLeft >= manyTimes; timesLeft -= manyTimes) {
            cardPos = doManyRoundsBackwards(cardPos);
        }
        for (; timesLeft > 0; timesLeft--) {
            cardPos = doOneRoundBackwards(cardPos);
        }
        return cardPos.toString();
    }

    private long doOneRoundSlow(long cardPos) {
        for (Function<Long, Long> action : actions) {
            cardPos = action.apply(cardPos);
        }
        return cardPos;
    }

    private BigInteger doOneRound(BigInteger cardPos) {
        return oneBase.add(cardPos.multiply(oneEach)).mod(CARD_COUNT);
    }

    private BigInteger doManyRounds(BigInteger cardPos) {
        for (int i = 0; i < manyTimes; i++) {
            cardPos = doOneRound(cardPos);
        }
        return cardPos;
    }

    private BigInteger doOneRoundBackwards(BigInteger cardPos) {
        return cardPos.subtract(oneBase).add(CARD_COUNT).mod(CARD_COUNT).multiply(oneInverse).mod(CARD_COUNT);
    }

    private BigInteger doManyRoundsBackwards(BigInteger cardPos) {
        return cardPos.subtract(manyBase).add(CARD_COUNT).mod(CARD_COUNT).multiply(manyInverse).mod(CARD_COUNT);
    }
}
