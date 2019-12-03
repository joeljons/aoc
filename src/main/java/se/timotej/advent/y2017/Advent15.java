package se.timotej.advent.y2017;

import java.io.IOException;

public class Advent15 {
    public static void main(String[] args) throws IOException {
//        new Advent15().calc(65, 8921);
        new Advent15().calc(722, 354);
    }

    private void calc(long a, long b) {
        int score = 0;
        for (int i = 0; i < 40000000; i++) {
            a = (a * 16807) % 2147483647;
            b = (b * 48271) % 2147483647;
            long left = a & 65535L;
            long right = b & 65535L;
            if (left == right) {
                score++;
            }
        }
        System.out.println("score = " + score);
    }
}