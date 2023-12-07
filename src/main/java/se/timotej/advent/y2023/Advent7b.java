package se.timotej.advent.y2023;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.*;

public class Advent7b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent7b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    static String sortFrom = "AKQT98765432J";
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
            for (int i = 0; i < 5; i++) {
                char card = cards.charAt(i);
                sortableCards += sortTo.charAt(sortFrom.indexOf(card));
            }
            majorPoint = rekMajorPoint(0, cards.toCharArray());
        }

        private int rekMajorPoint(int pos, char[] cards) {
            if (pos < 5) {
                char now = cards[pos];
                if (now == 'J') {
                    int best = 0;
                    for(int i=0;i<sortFrom.length()-1;i++){
                        cards[pos] = sortFrom.charAt(i);
                        best = Math.max(best, rekMajorPoint(pos + 1, cards));
                    }
                    cards[pos] = 'J';
                    return best;
                } else {
                    return rekMajorPoint(pos + 1, cards);
                }
            } else {
                Map<Character, Integer> count = new HashMap<>();
                for (char card : cards) {
                    count.merge(card, 1, Integer::sum);
                }
                int point = 0;
                if (count.size() == 5) {
                    point = 1; //high card
                } else if (count.size() == 4) {
                    point = 2; //one pair
                } else if (count.size() == 3) {
                    point = 3; //two pair
                    for (Integer value : count.values()) {
                        if (value == 3) {
                            point = 4; //three of a kind
                        }
                    }
                } else if (count.size() == 2) {
                    point = 5; //full house
                    for (Integer value : count.values()) {
                        if (value == 4) {
                            point = 6; //four of a kind
                        }
                    }
                } else if (count.size() == 1) {
                    point = 7; //five of a kind
                }

                return point;
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
