package se.timotej.advent.y2016;

import java.io.IOException;
import java.util.List;

public class Advent10b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent10b().calc(Online.get(10));
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    Bot[] bots = new Bot[1000];

    private int calc(List<String> strs) {
        //Collections.sort(strs);
        for (String str : strs) {
            String[] line = str.split(" ");
            if (line[0].equals("bot")) {
                int botnr = Integer.parseInt(line[1]);
                Bot bot = new Bot(botnr);
                bots[botnr] = bot;
                bot.giveLowTo = Integer.parseInt(line[6]) + (line[5].equals("bot") ? 0 : -1000);
                bot.giveHighTo = Integer.parseInt(line[11]) + (line[10].equals("bot") ? 0 : -1000);
            }
        }
        for (String str : strs) {
            String[] line = str.split(" ");
            if (line[0].equals("value")) {
                int value = Integer.parseInt(line[1]);
                int toBot = Integer.parseInt(line[5]);
                bots[toBot].take(value);
            }
        }
        return svar;
    }

    int svar = 1;


    private class Bot {
        int low;
        int high;
        int giveLowTo;
        int giveHighTo;
        private int botnr;

        public Bot(int botnr) {
            this.botnr = botnr;
        }

        private void take(int value) {
            if (low == 0) {
                low = value;
            } else {
                if (high != 0) {
                    System.out.println("ojoj");
                }
                if (low < value) {
                    high = value;
                } else {
                    high = low;
                    low = value;
                }
                if (giveLowTo >= 0) {
                    bots[giveLowTo].take(low);
                } else {
                    int output = giveLowTo + 1000;
                    System.out.println("Output " + output + " gets " + low);
                    if (output == 0 || output == 1 || output == 2) {
                        svar *= low;
                    }
                }
                if (giveHighTo >= 0) {
                    bots[giveHighTo].take(high);
                } else {
                    int output = giveHighTo + 1000;
                    System.out.println("Output " + output + " gets " + high);
                    if (output == 0 || output == 1 || output == 2) {
                        svar *= high;
                    }
                }
                low = 0;
                high = 0;
            }
        }
    }
}
