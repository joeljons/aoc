package se.timotej.advent.y2018;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent13b {

    public static void main(String[] args) throws IOException {
        String svar = new Advent13b().calc(Online.get());
        System.out.println("svar = " + svar);
//        Online.submit(svar);
    }

    private static int UP = 0;
    private static int RIGHT = 1;
    private static int DOWN = 2;
    private static int LEFT = 3;


    private static int[] dx = {0, 1, 0, -1}; // up, höger, ner, vänster
    private static int[] dy = {-1, 0, 1, 0};


    private String calc(List<String> strs) {
        List<Cart> carts = new ArrayList<>();
        {
            int y = 0;
            for (String str : strs) {
                for (int x = 0; x < str.length(); x++) {
                    char c = str.charAt(x);
                    if (c == '^') {
                        carts.add(new Cart(x, y, UP));
                    } else if (c == '>') {
                        carts.add(new Cart(x, y, RIGHT));
                    } else if (c == 'v') {
                        carts.add(new Cart(x, y, DOWN));
                    } else if (c == '<') {
                        carts.add(new Cart(x, y, LEFT));
                    }
                }
                y++;
            }
        }
        Map<Pair<Integer, Integer>, Cart> pos = new HashMap<>();
        while (carts.size() > 1) {
            List<Cart> toremove = new ArrayList<>();
            for (Cart cart : carts) {
                pos.remove(cart.getPos());
                if (toremove.contains(cart)) {
                    continue;
                }
                cart.go(strs);
                Pair<Integer, Integer> mypos = cart.getPos();
                if (pos.containsKey(mypos)) {
                    toremove.add(pos.get(mypos));
                    toremove.add(cart);
                    pos.remove(mypos);
                    System.out.println("BOOOM at " + mypos);
                } else {
                    pos.put(mypos, cart);
                }

            }
            carts.removeAll(toremove);
        }
        Cart cart = carts.get(0);
        return cart.x + "," + cart.y;
    }

    private class Cart {
        int x, y;
        int dir;
        int nextTurn = -1;

        Cart(int x, int y, int dir) {
            this.x = x;
            this.y = y;
            this.dir = dir;
        }

        void go(List<String> strs) {
            x += dx[dir];
            y += dy[dir];
            char c = strs.get(y).charAt(x);
            if (c == ' ') {
                throw new RuntimeException("ojoj");
            }
            if (c == '\\') {
                if (dir == UP) {
                    dir = LEFT;
                } else if (dir == RIGHT) {
                    dir = DOWN;
                } else if (dir == DOWN) {
                    dir = RIGHT;
                } else if (dir == LEFT) {
                    dir = UP;
                }
            } else if (c == '/') {
                if (dir == UP) {
                    dir = RIGHT;
                } else if (dir == RIGHT) {
                    dir = UP;
                } else if (dir == DOWN) {
                    dir = LEFT;
                } else if (dir == LEFT) {
                    dir = DOWN;
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
