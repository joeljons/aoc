package se.timotej.advent.y2016;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Advent19b {

    public static void main(String[] args) throws IOException, ExecutionException {
        int svar = new Advent19b().calc(Online.get().get(0));
        System.out.println("svar = " + svar);
        //Online.submit(svar);
    }


    private int calc(String str) {
        int elfs = Integer.parseInt(str);
        List<Integer> alive = IntStream.range(1, elfs + 1).boxed().collect(Collectors.toCollection(ArrayList::new));
        int pos = 0;
        int elfsKvar=elfs;
        while (alive.size() > 1) {
            if(elfsKvar--%1000==0){
                System.out.println("elfsKvar = " + elfsKvar);
            }
            int kill = (pos + alive.size() / 2) % alive.size();
            //System.out.println("alive.get(pos) = " + alive.get(pos));
            //System.out.println("alive.get(kill) = " + alive.get(kill));
            alive.remove(kill);
            if (kill > pos) {
                pos++;
            }
            if (pos >= alive.size()) {
                pos = 0;
            }
        }
        return alive.get(0);
    }

}