package se.timotej.advent.y2022;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Advent6 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent6().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int svar = 0;
        String str = strs.get(0);
        List<Character> chars = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            chars.add(c);
            if (chars.size() > 4) {
                chars.remove(0);
            }
            if (chars.size() == 4) {
                boolean ok = true;
                for (int j = 0; j < 4; j++) {
                    for (int k = j + 1; k < 4; k++) {
                        if (chars.get(j).equals(chars.get(k))) {
                            ok = false;
                            break;
                        }
                    }
                }
                if (ok) {
                    return i + 1;
                }
            }
        }
        return svar;
    }

}
