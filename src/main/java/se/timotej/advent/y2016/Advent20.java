package se.timotej.advent.y2016;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Advent20 {

    public static void main(String[] args) throws IOException, ExecutionException {
        long svar = new Advent20().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(String.valueOf(svar));
    }


    private long calc(List<String> strs) {
        List<Pair<Long, Long>> blocked = new ArrayList<>();
        for (String str : strs) {
            String[] line = str.split("-");
            blocked.add(Pair.of(Long.parseLong(line[0]), Long.parseLong(line[1])));
        }
        Collections.sort(blocked);
        long highestNonBlocked = 0;
        for (Pair<Long, Long> block : blocked) {
            if (highestNonBlocked < block.getLeft()) {
                return highestNonBlocked;
            }
            highestNonBlocked = Math.max(highestNonBlocked, block.getRight() + 1);
        }
        return highestNonBlocked;
    }

}