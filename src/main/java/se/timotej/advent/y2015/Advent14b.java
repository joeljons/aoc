package se.timotej.advent.y2015;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Advent14b {
    public static void main(String[] args) throws IOException {
        int svar = new Advent14b().calc(Online.get(14));
        System.out.println("svar = " + svar);
        Online.submit(14, 2, svar);
    }

    int calc(List<String> strs) {
        List<Horse> horses = new ArrayList<>();
        for (String str : strs) {
            String[] line = str.split(" ");
            horses.add(new Horse(str));
        }
        for (int i = 0; i < 2503; i++) {
            for (Horse horse : horses) {
                horse.step();
            }
            horses.sort(Comparator.comparingInt(Horse::getDistance).reversed());
            int best = horses.get(0).getDistance();
            for (Horse horse : horses) {
                if (horse.getDistance() == best) {
                    horse.score++;
                } else {
                    break;
                }
            }
        }
        horses.sort(Comparator.comparingInt(Horse::getScore).reversed());
        return horses.get(0).getScore();
    }

    private class Horse {
        int speed;
        int goTime;
        int restTime;
        int goKvar;
        int restKvar;
        int distance;
        int score;

        Horse(String str) {
            String[] line = str.split(" ");
            speed = Integer.parseInt(line[3]);
            goKvar = goTime = Integer.parseInt(line[6]);
            restTime = Integer.parseInt(line[13]);
            score = 0;
        }

        public int getDistance() {
            return distance;
        }

        int step() {
            if (goKvar > 0) {
                distance += speed;
                goKvar--;
                if (goKvar == 0) {
                    restKvar = restTime;
                }
            } else {
                restKvar--;
                if (restKvar == 0) {
                    goKvar = goTime;
                }
            }
            return distance;
        }

        int getScore() {
            return score;
        }
    }
}
