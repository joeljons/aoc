package se.timotej.advent.y2023;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.*;

public class Advent7 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent7().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    static String sortFrom = "AKQJT98765432";
    static String sortTo = StringUtils.reverse("abcdefghijklm");

    private long calc(List<String> strs) {
        long svar = 0;
        List<Hand> hands = new ArrayList<>();
        for (String str : strs) {
            String[] split = str.split(" ");
            hands.add(new Hand(split[0], Integer.parseInt(split[1])));
        }
        Collections.sort(hands);
        for (int i = 0; i < hands.size(); i++) {
            Hand hand = hands.get(i);
            svar += (i + 1) * hand.bidding;
        }

        return svar;
    }

    private static class Hand implements Comparable<Hand> {
        int bidding;
        int majorPoint;
        String sortableCards;

        public Hand(String cards, int bidding) {
            this.bidding = bidding;
            sortableCards = "";
            Map<Character, Integer> count = new HashMap<>();
            for (int i = 0; i < 5; i++) {
                char card = cards.charAt(i);
                count.merge(card, 1, Integer::sum);
                sortableCards += sortTo.charAt(sortFrom.indexOf(card));
            }
            if (count.size() == 5) {
                majorPoint = 1; //high card
            } else if (count.size() == 4) {
                majorPoint = 2; //one pair
            } else if (count.size() == 3) {
                majorPoint = 3; //two pair
                for (Integer value : count.values()) {
                    if (value == 3) {
                        majorPoint = 4; //three of a kind
                    }
                }
            } else if (count.size() == 2) {
                majorPoint = 5; //full house
                for (Integer value : count.values()) {
                    if (value == 4) {
                        majorPoint = 6; //four of a kind
                    }
                }
            } else if (count.size() == 1) {
                majorPoint = 7; //five of a kind
            }
        }

        @Override
        public int compareTo(Hand o) {
            int cmp = Integer.compare(majorPoint, o.majorPoint);
            if (cmp != 0) {
                return cmp;
            } else {
                return sortableCards.compareTo(o.sortableCards);
            }
        }
    }
}
