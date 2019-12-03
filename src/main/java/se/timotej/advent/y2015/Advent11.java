package se.timotej.advent.y2015;

import java.io.IOException;

public class Advent11 {
    public static void main(String[] args) throws IOException {
        String svar = new Advent11().calc(Online.get(11).get(0));
        System.out.println("svar = " + svar);
        //Online.submit(11, 1, svar);
        svar = new Advent11().calc(svar);
        System.out.println("svar = " + svar);
        Online.submit(11, 2, svar);
    }

    private String calc(String str) {
        char[] chars = str.toCharArray();
        do {
            increase(chars);
        } while (!valid(chars));
        return new String(chars);
    }

    private void increase(char[] chars) {
        for (int p = chars.length - 1; p >= 0; p--) {
            chars[p]++;
            if (chars[p] > 'z') {
                chars[p] = 'a';
            } else {
                break;
            }
        }
    }

    private boolean valid(char[] chars) {
        boolean increasing = false;
        for (int i = 0; i < chars.length - 2; i++) {
            if (chars[i] + 1 == chars[i + 1] && chars[i + 1] + 1 == chars[i + 2]) {
                increasing = true;
                break;
            }
        }
        if (!increasing) {
            return false;
        }
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == 'i' || chars[i] == 'o' || chars[i] == 'l') {
                return false;
            }
        }
        int likas = 0;
        for (int i = 0; i < chars.length - 1; i++) {
            if (chars[i] == chars[i + 1]) {
                likas++;
                i++;
            }
        }
        return likas >= 2;
    }
}