package se.timotej.advent.y2018;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Advent6b_second {

    public static void main(String[] args) throws IOException {
        int svar = new Advent6b_second().calc(Online.get(6));

        System.out.println("svar = " + svar);
//        Online.submit(svar);
    }

    int[][] g = new int[400][400];
    List<Pair<Integer, Integer>> input = new ArrayList<>();

    private int calc(List<String> strs) {
        for (String str : strs) {
            String[] line = str.split("[, ]+");
            int x = Integer.parseInt(line[0]);
            int y = Integer.parseInt(line[1]);
            input.add(Pair.of(x,y));
        }
        int svar = 0;
        for (int y = 0; y < 400; y++) {
            for (int x = 0; x < 400; x++) {
                int dist = 0;
                for (Pair<Integer, Integer> pos : input) {
                    dist += Math.abs(x - pos.getLeft()) + Math.abs(y - pos.getRight());
                }
                if(dist<10000)svar++;
            }
        }
        return svar;
}

}
//fel 44634, 44605
