package se.timotej.advent.y2015;

public class Advent25 {
    public static void main(String[] args) {
        int svar = new Advent25().calc(3010, 3019);
        System.out.println("svar = " + svar);
        Online.submit(25, 1, svar);
    }

    private int calc(int wantedRow, int wantedCol) {
        int r = 1;
        int c = 1;
        long val = 20151125;
        do {
            c++;
            r--;
            if (r == 0) {
                r = c;
                c = 1;
            }
            val = (val * 252533) % 33554393;
        } while (r != wantedRow || c != wantedCol);
        return (int) val;
    }
}