package se.timotej.advent.y2016;

import java.io.IOException;
import java.util.List;

public class Advent10 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent10().calc(Online.get(10));
        System.out.println("svar = " + svar);
        //Online.submit(svar);
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
                bot.giveLowTo = Integer.parseInt(line[6]) * (line[5].equals("bot") ? 1 : -1);
                bot.giveHighTo = Integer.parseInt(line[11]) * (line[10].equals("bot") ? 1 : -1);
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
        return 0;
    }


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
                if (low < value) {
                    high = value;
                } else {
                    high = low;
                    low = value;
                }
                if(low == 17 && high == 61){
                    Online.submit(10, 1, botnr);
                    System.exit(0);
                }
                int theLow = low;
                low = 0;
                int theHigh = high;
                high = 0;
                if (giveLowTo > 0) {
                    bots[giveLowTo].take(theLow);

                } else {
                    System.out.println("Output " + (-giveLowTo) + " gets " + theLow);
                }
                if (giveHighTo > 0) {
                    bots[giveHighTo].take(theHigh);
                } else {
                    System.out.println("Output " + (-giveHighTo) + " gets " + theHigh);
                }
            }
        }
    }
}
