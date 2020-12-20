package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Advent20 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent20().calc(Online.get());
        System.out.println("svar = " + svar);
//        Online.submit(svar);
    }

    List<Img> imgs = new ArrayList<>();

    private long calc(List<String> strs) {
        long sum = 0;
        System.out.println("Util.splitByDoubleNewline(strs).size() = " + Util.splitByDoubleNewline(strs).size());
        for (List<String> strings : Util.splitByDoubleNewline(strs)) {
            int nr = Util.findAllInts(strings.get(0)).get(0);
            char[][] bild = new char[10][];
            for (int i = 1; i <= 10; i++) {
                bild[i - 1] = strings.get(i).toCharArray();
            }
            imgs.add(new Img(nr, bild));
        }
        Arrays.fill(taken, -1);
        calc(0, 1);
        return sum;
    }

    int[] taken = new int[144];
    char[][][][] g = new char[12][12][][];

    private void calc(int i, long prod) {
        if(i==144){
            System.out.println("prod = " + prod);
            return;
        }
        int y = i / 12;
        int x = i % 12;
        for (int j = 0; j < 144; j++) {
            if (taken[j] != -1) {
                continue;
            }
            taken[j] = i;
            long nextProd = prod;
            Img ourImg = imgs.get(j);
            if(i==0||i==11||i==143||i==143-11){
                nextProd *= ourImg.nr;
            }
            for (int variant = 0; variant < 8; variant++) {
                if (fits(x, y, ourImg.variants[variant])) {
                    g[y][x] = ourImg.variants[variant];
                    calc(i + 1, nextProd);
                }
            }
            taken[j] = -1;
        }
    }

    private boolean fits(int x, int y, char[][] variant) {
        if (x > 0) {
            char[][] left = g[y][x - 1];
            for (int yy = 0; yy < 10; yy++) {
                if (left[yy][9] != variant[yy][0]) {
                    return false;
                }
            }
        }
        if (y > 0) {
            char[][] top = g[y - 1][x];
            for (int xx = 0; xx < 10; xx++) {
                if (top[9][xx] != variant[0][xx]) {
                    return false;
                }
            }
        }
        return true;
    }

    private static class Img {
        int nr;
        char[][] bild;

        char[][][] variants = new char[8][][];

        public Img(int nr, char[][] bild) {
            this.nr = nr;
            this.bild = bild;
            variants[0] = bild;
            for (int i = 1; i < 4; i++) {
                variants[i] = rotate(variants[i - 1]);
            }
            for (int i = 0; i < 4; i++) {
                variants[4 + i] = flip(variants[i]);
            }
        }

        private char[][] rotate(char[][] from) {
            char[][] to = new char[10][10];
            for (int y = 0; y < 10; y++) {
                for (int x = 0; x < 10; x++) {
                    to[y][x] = from[9 - x][y];
                }
            }
            return to;
        }

        private char[][] flip(char[][] from) {
            char[][] to = new char[10][10];
            for (int y = 0; y < 10; y++) {
                for (int x = 0; x < 10; x++) {
                    to[y][x] = from[y][9 - x];
                }
            }
            return to;
        }
    }
}
