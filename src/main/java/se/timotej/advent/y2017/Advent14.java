package se.timotej.advent.y2017;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Advent14 {
    public static void main(String[] args) throws IOException {
        new Advent14().calc(Online.get(14));
    }

    private void calc(List<String> strs) {
        String str = strs.get(0);
        int sum = 0;
        for(int i=0;i<128;i++){
            String sToHash = str + "-" + i;
            sum += calc2(256, sToHash);
        }
        System.out.println("sum = " + sum);
    }



    private int calc2(int listLen, String str) {
        String hash = new Advent10b().calc(256, Collections.singletonList(str));
        int ut = 0;
        for (int i = 0; i < 32; i++) {
            int sum = Character.digit(hash.charAt(i), 16);
            ut += (sum & 8) >> 3;
            ut += (sum & 4) >> 2;
            ut += (sum & 2) >> 1;
            ut += sum & 1;
        }
        return ut;
    }

}