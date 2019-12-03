package se.timotej.advent.y2016;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class Advent15 {

    public static void main(String[] args) throws IOException, ExecutionException {
        int svar = new Advent15().calc(Online.get(15));
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        List<Disc> discs = strs.stream().map(Disc::new).collect(Collectors.toList());
        int t = 0;
        while (!discs.stream().allMatch(Disc::isAligned)) {
            discs.forEach(Disc::rotate);
            t++;
        }
        return t;
    }

    private class Disc {
        int nr;
        int size;
        int position;

        public Disc(String str) {
            String[] line = str.split("[ #.]");
            nr = Integer.parseInt(line[2]);
            size = Integer.parseInt(line[4]);
            position = Integer.parseInt(line[12]);
        }

        public void rotate() {
            position = (position + 1) % size;
        }

        public boolean isAligned() {
            return (position + nr) % size == 0;
        }
    }

}