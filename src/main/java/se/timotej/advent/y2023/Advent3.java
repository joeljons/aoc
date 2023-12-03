package se.timotej.advent.y2023;

import java.io.IOException;
import java.util.List;

public class Advent3 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent3().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dx = new int[]{0, 1, 1, 1, 0, -1, -1, -1};
    int[] dy = new int[]{-1, -1, 0, 1, 1, 1, 0, -1};

    private int calc(List<String> strs) {
        int svar = 0;
        int maxy = strs.size();
        int maxx = strs.get(0).length();
        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                char c = strs.get(y).charAt(x);
                if (Character.isDigit(c)) {
                    int tal = 0;
                    boolean connected = false;
                    while (Character.isDigit(c) && x < maxx) {
                        tal = 10 * tal + c - '0';
                        for (int dir = 0; dir < 8; dir++) {
                            int nyx = x + dx[dir];
                            int nyy = y + dy[dir];
                            if (nyx >= 0 && nyx < maxx && nyy >= 0 && nyy < maxy && !Character.isDigit(strs.get(nyy).charAt(nyx)) && strs.get(nyy).charAt(nyx) != '.') {
                                connected = true;
                            }
                        }
                        x++;
                        if (x < maxx) {
                            c = strs.get(y).charAt(x);
                        }
                    }
                    if (connected) {
                        svar += tal;
                    }
                }
            }
        }
        return svar;
    }
}
