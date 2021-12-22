package se.timotej.advent.y2021;

import java.io.IOException;
import java.util.List;

public class Advent21 {

    public static void main(String[] args) throws IOException {
        var svar = new Advent21().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int[] pos = new int[]{9, 6};
        int[] score = new int[]{0, 0};
        int rolls = 0;
        int nextScore = 1;
        while (true) {
            for (int turn = 0; turn < 2; turn++) {
                int sum = 0;
                for (int r = 0; r < 3; r++) {
                    sum += nextScore;
                    rolls++;
                    nextScore++;
                    if (nextScore > 100) {
                        nextScore = 1;
                    }
                }
                pos[turn] = (pos[turn] + sum) % 10;
                score[turn] += pos[turn] + 1;
                if (score[turn] >= 1000) {
                    return rolls * score[(turn + 1) % 2];
                }
            }
        }
    }

}
