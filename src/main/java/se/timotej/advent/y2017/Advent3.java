package se.timotej.advent.y2017;

import java.io.IOException;

public class Advent3 {
    public static void main(String[] args) throws IOException {
        new Advent3().calc(1);
        new Advent3().calc(12);
        new Advent3().calc(23);
        new Advent3().calc(1024);
        new Advent3().calc(Integer.parseInt(Online.get(3).get(0)));
    }

    int x = 0;
    int y = 0;
    int num = 1;
    int tal;

    private void calc(int tal) {
        this.tal = tal;
        for(int i=1;num<=tal;i+=2) {
            walk(1, 0, i);
            walk(0, 1, i);
            walk(-1, 0, i + 1);
            walk(0, -1, i + 1);
        }
    }

    private void walk(int dx, int dy, int dist) {
        for(int j=0;j<dist;j++){
            if(num == tal){
                System.out.println(Math.abs(x)+Math.abs(y));
            }
            x += dx;
            y += dy;
            num++;
        }
    }
}
