package se.timotej.advent.y2021;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Advent4 {

    public static void main(String[] args) throws IOException {
        var svar = new Advent4().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int[] numbers = Util.intArrayComma(strs.get(0));
        List<Bricka> bricks = new ArrayList<>();
        for (int i = 2; i < strs.size(); i += 6) {
            bricks.add(new Bricka(i, strs));
        }
        for (int number : numbers) {
            for (Bricka brick : bricks) {
                if (brick.supply(number)) {
                    return brick.score() * number;
                }
            }
        }
        return 0;
    }

    private static class Bricka {
        boolean[][] taken = new boolean[5][5];
        int[][] board = new int[5][];

        public Bricka(int i, List<String> strs) {
            for (int j = 0; j < 5; j++) {
                board[j] = Util.intArray(strs.get(i + j));
            }
        }

        public boolean supply(int number) {
            for (int y = 0; y < 5; y++) {
                for (int x = 0; x < 5; x++) {
                    if (board[y][x] == number) {
                        taken[y][x] = true;
                        boolean klar = true;
                        for (int yy = 0; yy < 5; yy++) {
                            if (!taken[yy][x]) {
                                klar = false;
                                break;
                            }
                        }
                        if (klar) {
                            return true;
                        }
                        boolean klar2 = true;
                        for (int xx = 0; xx < 5; xx++) {
                            if (!taken[y][xx]) {
                                klar2 = false;
                                break;
                            }
                        }
                        if (klar2) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        public int score() {
            int sum = 0;
            for (int y = 0; y < 5; y++) {
                for (int x = 0; x < 5; x++) {
                    if (!taken[y][x]) {
                        sum += board[y][x];
                    }
                }
            }
            return sum;
        }

    }
}

