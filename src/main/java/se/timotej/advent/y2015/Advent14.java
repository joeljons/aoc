package se.timotej.advent.y2015;

import java.io.IOException;
import java.util.List;

public class Advent14 {
    public static void main(String[] args) throws IOException {
        int svar = new Advent14().calc(Online.get(14));
        System.out.println("svar = " + svar);
        Online.submit(14, 1, svar);
    }

    int calc(List<String> strs) {
        int best = 0;
        for (String str : strs) {
            String[] line = str.split(" ");
            int speed = Integer.parseInt(line[3]);
            int goTime = Integer.parseInt(line[6]);
            int restTime = Integer.parseInt(line[13]);
            int score = 0;
            int goKvar = goTime;
            int restKvar = 0;
            for(int i=0;i<2503;i++){
                if(goKvar>0){
                    score += speed;
                    goKvar--;
                    if(goKvar==0){
                        restKvar = restTime;
                    }
                } else {
                    restKvar--;
                    if(restKvar == 0){
                        goKvar = goTime;
                    }
                }
            }
            if (score > best) {
                best = score;
            }
        }
        return best;
    }
}
