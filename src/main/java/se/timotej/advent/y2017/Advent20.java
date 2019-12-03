package se.timotej.advent.y2017;

import java.io.IOException;
import java.util.List;

public class Advent20 {
    public static void main(String[] args) throws IOException {
        //new Advent21().calc(Collections.singletonList("3"));
        new Advent20().calc(Online.get(20));
    }

    private void calc(List<String> strs) {
        int i=0;
        int svar = -1;
        int best = 0;
        for (String str : strs) {
            String s = str.split(", a=")[1];
            String[] split = s.replace("<", "").replace(">", "").split(",");
            int sum = Math.abs(Integer.parseInt(split[0])) + Math.abs(Integer.parseInt(split[1])) + Math.abs(Integer.parseInt(split[2]));
            if(svar == -1 || sum<best){
                best = sum;
                svar = i;
            }
            i++;
        }
        Online.submit(20, 1, svar);
    }
}