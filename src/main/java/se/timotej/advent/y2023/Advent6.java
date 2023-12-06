package se.timotej.advent.y2023;

import java.io.IOException;
import java.util.List;

public class Advent6 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent6().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 1;
        List<Integer> times = Util.findAllInts(strs.get(0));
        List<Integer> distances = Util.findAllInts(strs.get(1));
        for (int raceNum = 0; raceNum < times.size(); raceNum++) {
            int wins = 0;
            Integer raceTime = times.get(raceNum);
            Integer raceDistance = distances.get(raceNum);
            for (int buttonTime = 0; buttonTime <= raceTime; buttonTime++) {
                int myDistance = (raceTime - buttonTime) * buttonTime;
                if (myDistance > raceDistance) {
                    wins++;
                }
            }
            svar *= wins;
        }

        return svar;
    }
}
