package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Advent23 {

    public static void main(String[] args) throws IOException {
        String svar = new Advent23().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private String calc(List<String> strs) {
        String now = strs.get(0);
        for (int i = 0; i < 100; i++) {
            now = move(now);
        }
        int index = now.indexOf("1");
        return now.substring(index + 1) + now.substring(0, index);
    }

    private String move(String from) {
        List<Integer> g = new ArrayList<>();
        for (int i = 0; i < from.length(); i++) {
            char c = from.charAt(i);
            g.add(c - '0');
        }
        List<Integer> pickup = new ArrayList<>();
        pickup.add(g.remove(1));
        pickup.add(g.remove(1));
        pickup.add(g.remove(1));
        int destination = g.get(0) - 1;
        if (destination == 0) {
            destination = 9;
        }
        while (pickup.contains(destination)) {
            destination--;
            if (destination == 0) {
                destination = 9;
            }
        }
        int destIndex = g.indexOf(destination);
        g.add(destIndex+1, pickup.get(2));
        g.add(destIndex+1, pickup.get(1));
        g.add(destIndex+1, pickup.get(0));
        String s = "";
        for(int i=1;i<g.size();i++){
            s += g.get(i);
        }
        s += g.get(0);
        return s;
    }
}