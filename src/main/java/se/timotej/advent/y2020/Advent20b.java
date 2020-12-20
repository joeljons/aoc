package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Advent20b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent20b().calc(Online.get());
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

        monster[0] = "                  # ".toCharArray();
        monster[1] = "#    ##    ##    ###".toCharArray();
        monster[2] = " #  #  #  #  #  #   ".toCharArray();

        calc(0, 1);
        return sum;
    }

    int[] taken = new int[144];
    char[][][][] g = new char[12][12][][];

    int MAX = 12 * 8;
    char[][] water = new char[MAX][MAX];
    char[][] monster = new char[3][];

    private void calc(int i, long prod) {
        if (i == 144) {
            System.out.println("prod = " + prod);
            for (int y = 0; y < 12; y++) {
                for (int x = 0; x < 12; x++) {
                    for (int yy = 1; yy < 9; yy++) {
                        for (int xx = 1; xx < 9; xx++) {
                            water[8 * y + yy - 1][8 * x + xx - 1] = g[y][x][yy][xx];
                        }
                    }
                }
            }
            int count = monsterCount();
            System.out.println("count = " + count);
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
            if (i == 0 || i == 11 || i == 143 || i == 143 - 11) {
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

    private int monsterCount() {
        for (int y = 0; y < MAX - 2; y++) {
            for (int x = 0; x < MAX - 19; x++) {
                boolean ok = true;
                for (int yy = 0; yy < 3; yy++) {
                    for (int xx = 0; xx < 20; xx++) {
                        if (monster[yy][xx] == '#' && water[y + yy][x + xx] == '.') {
                            ok = false;
                            break;
                        }
                    }
                    if (!ok) {
                        break;
                    }
                }
                if (ok) {
                    for (int yy = 0; yy < 3; yy++) {
                        for (int xx = 0; xx < 20; xx++) {
                            if (monster[yy][xx] == '#') {
                                water[y + yy][x + xx] = 'O';
                            }
                        }
                    }
                }
            }
        }

        int count = 0;
        for (int y = 0; y < MAX; y++) {
            for (int x = 0; x < MAX; x++) {
                if (water[y][x] == '#') {
                    count++;
                }
            }
        }
        return count;
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
