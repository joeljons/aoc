package se.timotej.advent.y2023;

import java.io.IOException;
import java.util.List;

public class Advent6b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent6b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 1;
        List<Long> times = Util.findAllLongs(strs.get(0).replaceAll(" ", ""));
        List<Long> distances = Util.findAllLongs(strs.get(1).replaceAll(" ", ""));
        for (int raceNum = 0; raceNum < times.size(); raceNum++) {
            int wins = 0;
            Long raceTime = times.get(raceNum);
            Long raceDistance = distances.get(raceNum);
            for (long buttonTime = 0; buttonTime <= raceTime; buttonTime++) {
                long myDistance = (raceTime - buttonTime) * buttonTime;
                if (myDistance > raceDistance) {
                    wins++;
                }
            }
            svar *= wins;
        }

        return svar;
    }
}
