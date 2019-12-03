package se.timotej.advent.y2016;

import java.io.IOException;
import java.util.List;

public class Advent8 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent8().calc(Online.get());
        System.out.println("svar = " + svar);
        //Online.submit(svar);
        //Online.submit(8,2,"ZFHFSFOGPO");
    }

    private int calc(List<String> strs) {
        boolean[][] g = new boolean[6][50];
        for (String str : strs) {
            String[] line = str.split("\\D+");
            int A = Integer.parseInt(line[1]);
            int B = Integer.parseInt(line[2]);
            if (str.startsWith("rect")) {
                for (int x = 0; x < A; x++) {
                    for (int y = 0; y < B; y++) {
                        g[y][x] = true;
                    }
                }
            } else if (str.startsWith("rotate row")) {
                for (int b = 0; b < B; b++) {
                    boolean last = g[A][49];
                    for (int i = 49; i > 0; i--) {
                        g[A][i] = g[A][i - 1];
                    }
                    g[A][0] = last;
                }
            } else if (str.startsWith("rotate column")) {
                for (int b = 0; b < B; b++) {
                    boolean last = g[5][A];
                    for (int i = 5; i > 0; i--) {
                        g[i][A] = g[i - 1][A];
                    }
                    g[0][A] = last;
                }
            } else {
                throw new RuntimeException(str);
            }
        }
        int count = 0;
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 50; x++) {
                if (g[y][x]) {
                    count++;
                    System.out.print("#");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        return count;
    }
}
