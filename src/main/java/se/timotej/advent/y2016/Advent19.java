package se.timotej.advent.y2016;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Advent19 {

    public static void main(String[] args) throws IOException, ExecutionException {
        int svar = new Advent19().calc(Online.get().get(0));
        System.out.println("svar = " + svar);
        //Online.submit(svar);
    }


    private int calc(String str) {
        int elfs = Integer.parseInt(str);
        List<Integer> alive = IntStream.range(1, elfs + 1).boxed().collect(Collectors.toCollection(LinkedList::new));
        while (alive.size() > 1) {
            Iterator<Integer> it = alive.iterator();
            while (it.hasNext()) {
                it.next();
                if (it.hasNext()) {
                    it.next();
                    it.remove();
                } else {
                    alive.remove(0);
                    break;
                }
            }
        }
        return alive.get(0);
    }

}