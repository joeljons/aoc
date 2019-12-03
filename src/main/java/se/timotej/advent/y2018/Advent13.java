package se.timotej.advent.y2018;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent13 {

    public static void main(String[] args) throws IOException {
        String svar = new Advent13().calc(Online.get());
        System.out.println("svar = " + svar);
//        Online.submit(svar);
    }

    int UP = 0;
    int HOGER = 1;
    int NER = 2;
    int VANSTER = 3;


    int[] dx = {0, 1, 0, -1}; // up, höger, ner, vänster
    int[] dy = {-1, 0, 1, 0};


    private String calc(List<String> strs) {
        int svar = 0;
        List<Cart> carts = new ArrayList<>();
        {
            int y = 0;
            for (String str : strs) {
                for (int x = 0; x < str.length(); x++) {
                    char c = str.charAt(x);
                    if (c == '^') {
                        carts.add(new Cart(x, y, 0));
                    } else if (c == '>') {
                        carts.add(new Cart(x, y, 1));
                    } else if (c == 'v') {
                        carts.add(new Cart(x, y, 2));
                    } else if (c == '<') {
                        carts.add(new Cart(x, y, 3));
                    }
                }
                y++;
            }
        }
        for (int tick = 0; ; tick++) {
            System.out.println("tick = " + tick);
            Set<Pair<Integer, Integer>> pos = new HashSet<>();
            for (Cart cart : carts) {
                cart.go(strs);
                Pair<Integer, Integer> mypos = cart.getPos();
                if(!pos.add(mypos)){
                    return mypos.getLeft() + "," + mypos.getRight();
                }
            }

        }
    }

    private class Cart {
        int x, y;
        int dir;
        int nextTurn = -1;

        public Cart(int x, int y, int dir) {
            this.x = x;
            this.y = y;
            this.dir = dir;
        }

        public void go(List<String> strs) {
            x += dx[dir];
            y += dy[dir];
            char c = strs.get(y).charAt(x);
            if (c == ' ') {
                throw new RuntimeException("ojoj");
            }
            if (c == '\\') {
                if (dir == UP) {
                    dir = VANSTER;
                } else if (dir == HOGER) {
                    dir = NER;
                } else if (dir == NER) {
                    dir = HOGER;
                } else if (dir == VANSTER) {
                    dir = UP;
                }
            } else if (c == '/') {
                if (dir == UP) {
                    dir = HOGER;
                } else if (dir == HOGER) {
                    dir = UP;
                } else if (dir == NER) {
                    dir = VANSTER;
                } else if (dir == VANSTER) {
                    dir = NER;
                }
            } else if (c == '+') {
                dir = (dir + 4 + nextTurn) % 4;
                nextTurn++;
                if (nextTurn > 1) {
                    nextTurn = -1;
                }
            }
        }

        public Pair<Integer, Integer> getPos() {
            return Pair.of(x, y);
        }
    }
}
