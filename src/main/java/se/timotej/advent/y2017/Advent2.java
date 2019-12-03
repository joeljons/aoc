package se.timotej.advent.y2017;

import java.io.IOException;
import java.util.List;

public class Advent2 {
    public static void main(String[] args) throws IOException {
        List<String> strs = Online.get(2);
        int sum=0;
        for (String row : strs) {
            String[] split = row.split("\t");
            int[] rad = new int[split.length];
            for(int i=0;i<split.length;i++){
                rad[i] = Integer.parseInt(split[i]);
            }
            sum += kollaRad(rad);
        }
        System.out.println("sum = " + sum);
    }

    private static int kollaRad(int[] rad) {
        for(int i=0;i<rad.length;i++){
            for(int j=0;j<rad.length;j++){
                if(i==j)continue;
                if(rad[i]%rad[j]==0)return rad[i]/rad[j];
            }
        }
        throw new RuntimeException();
    }
}
