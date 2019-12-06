package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent6 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent6().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        Map<String, String> orbits = new HashMap<>();
        for (String str : strs) {
            String[] split = str.split("\\)");
            orbits.put(split[1], split[0]);
        }
        int svar = 0;
        for (String s : orbits.keySet()) {
            while(!s.equals("COM")){
                svar++;
                s = orbits.get(s);
            }
        }
        return svar;
    }


}
