package se.timotej.advent.y2024;

import java.io.IOException;
import java.util.List;

public class Advent22b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent22b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;
        int[][] g = new int[strs.size()][2001];
        for (int i = 0; i < strs.size(); i++) {
            long secret = Long.parseLong(strs.get(i));
            for (int j = 0; j < 2001; j++) {
                g[i][j] = (int) (secret % 10);
                secret = nextSecret(secret);
            }
        }

        for (int a = -9; a <= 9; a++) {
            for (int b = -9; b <= 9; b++) {
                if (a + b < -9 || a + b > 9) {
                    continue;
                }
                System.out.printf("%3d%3d%n", a, b);
                for (int c = -9; c <= 9; c++) {
                    if (a + b + c < -9 || a + b + c > 9) {
                        continue;
                    }
                    for (int d = -9; d <= 9; d++) {
                        if (a + b + c + d < -9 || a + b + c + d > 9) {
                            continue;
                        }
                        long moneyNow = 0;
                        for (int i = 0; i < strs.size(); i++) {
                            for (int j = 4; j < 2001; j++) {
                                if (g[i][j] - g[i][j - 1] == a
                                        && g[i][j - 1] - g[i][j - 2] == b
                                        && g[i][j - 2] - g[i][j - 3] == c
                                        && g[i][j - 3] - g[i][j - 4] == d) {
                                    moneyNow += g[i][j];
                                    break;
                                }
                            }
                        }
                        if (moneyNow > svar) {
                            System.out.printf("%3d%3d%3d%3d %d%n", a, b, c, d, moneyNow);
                            svar = moneyNow;
                        }

                    }
                }
            }
        }


        return svar;
    }

    private static long nextSecret(long secret) {
        secret ^= secret * 64;
        secret %= 16777216;
        secret ^= secret / 32;
        secret %= 16777216;
        secret ^= secret * 2048;
        secret %= 16777216;
        return secret;
    }
}
