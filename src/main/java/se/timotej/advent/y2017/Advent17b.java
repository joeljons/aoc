package se.timotej.advent.y2017;

import java.io.IOException;
import java.util.List;

public class Advent17b {
    public static void main(String[] args) throws IOException {
        //new Advent17b().calc(Collections.singletonList("3"));
        new Advent17b().calc(Online.get(17));
    }

    private void calc(List<String> strs) {
        String str = strs.get(0);
        int stepLen = Integer.parseInt(str);
        int pos = 0;
        int svar = -1;
        for(int i=1;i<=50000000;i++){
            pos = (pos+stepLen)%i;
            if(pos == 0){
                svar = i;
            }
            pos++;
        }
        System.out.println("svar = " + svar);

    }
}