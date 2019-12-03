package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Advent4 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent4().calc(Online.get());

        System.out.println("svar = " + svar);
        //Online.submit(svar);
    }

    int[][] sovs = new int[10000][60];

    private int calc(List<String> strs) {
        Collections.sort(strs);
        int svar = 0;
        int guardOnShift = -1;
        int somnade = -1;
        for (String str : strs) {
            System.out.println(str);
            String[] line = str.split("[# ]");
            int minute = Integer.parseInt(str.substring(15, 17));
            if(str.contains("begins shift")){
                guardOnShift = Integer.parseInt(line[4]);
                if(somnade != -1){
                    System.out.println("somnade = " + somnade);
                }
            }
            if(str.contains("falls asleep")){
                somnade = minute;
            }
            if(str.contains("wakes up")){
                for(int i=somnade; i<minute;i++){
                    sovs[guardOnShift][i]++;
                }
                somnade = -1;
            }
        }
        int mestSov = 0;
        int mestMinut = 0;
        int mestGuard=0;
        for (int i = 0; i < sovs.length; i++) {
            int[] sov = sovs[i];
            int sum=0;
            for (int i1 : sov) {
                sum += i1;
            }
            if(sum > mestSov) {
                mestSov = sum;
                mestGuard = i;
                mestMinut = 0;
                for (int j = 0; j < sov.length; j++) {
                    if (sov[j] >= sov[mestMinut]) {
                        mestMinut = j;
                    }

                }
            }
        }

        return mestGuard * mestMinut;
    }

}
