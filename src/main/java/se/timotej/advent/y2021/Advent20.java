package se.timotej.advent.y2021;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;

public class Advent20 {

    public static void main(String[] args) throws IOException {
        var svar = new Advent20().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int maxy;
    int maxx;
    int i;

    private int calc(List<String> strs) {
        int sum = 0;
        String key = strs.get(0);
        char[][] nu = new char[1000][1000];
        maxy = nu.length;
        maxx = nu[0].length;
        for (int y = 0; y < maxy; y++) {
            nu[y] = StringUtils.repeat(".", maxx).toCharArray();
        }
        i = 450;
        for (String str : strs.subList(2, strs.size())) {
            for (int x = 0; x < str.length(); x++) {
                nu[i][x + 450] = str.charAt(x);
            }
            i++;
        }
        for (i = 0; i < 2; i++) {
            char[][] next = new char[maxy][maxx];
            for (int y = 0; y < maxy; y++) {
                for (int x = 0; x < maxx; x++) {
                    String pixel = get(nu, y - 1, x - 1) + get(nu, y - 1, x) + get(nu, y - 1, x + 1)
                            + get(nu, y, x - 1) + get(nu, y, x) + get(nu, y, x + 1)
                            + get(nu, y + 1, x - 1) + get(nu, y + 1, x) + get(nu, y + 1, x + 1);
                    int position = Integer.parseInt(pixel.replace(".", "0").replace("#", "1"), 2);
                    next[y][x] = key.charAt(position);
                }
            }
            nu = next;
        }
        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                if (nu[y][x] == '#') {
                    sum++;
                }
            }
        }
        return sum;
    }

    private String get(char[][] nu, int y, int x) {
        if (y < 0 || y >= maxy || x < 0 || x >= maxx) {
            return i % 2 == 0 ? "." : "#";
        } else {
            return nu[y][x] + "";
        }
    }

}
