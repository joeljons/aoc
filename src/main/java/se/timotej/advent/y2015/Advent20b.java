package se.timotej.advent.y2015;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Advent20b {
    public static void main(String[] args) throws IOException {
        int svar = new Advent20b().calc(Online.get().get(0));
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int calc(String str) {
        long input = Long.parseLong(str);
        int nu = 0;
        long best = 0;
        while (true) {
            nu += 10 * 2 * 2 * 2;
            long houseGets = 0;
            for (int i = 1; i <= nu; i++) {
                if (nu % i == 0 && nu / i <= 50) {
                    houseGets += i * 11;
                }
            }
            //System.out.println(String.format("House %d got %d presents.", nu, houseGets));
            if (houseGets > best) {
                best = houseGets;
                System.out.println(String.format("House %d got %d presents.", nu, houseGets));
            }
            if (houseGets >= input) {
                return nu;
            }
        }
    }

    int calc_old(String str) {
        long input = Long.parseLong(str) / 10;
        List<Integer> elves = new ArrayList<>();
        elves.add(0);
        int nu = 0;
        while (true) {
            nu++;
            long houseGets = 0;
            elves.add(nu);
            for (int i = 1; i < elves.size(); i++) {
                if (elves.get(i) == nu) {
                    houseGets += i;
                    elves.set(i, nu + i);
                }
            }
            //System.out.println(String.format("House %d got %d presents.", nu, houseGets));
            if (houseGets >= input) {
                return nu;
            }
        }
    }
}
