package se.timotej.advent.y2016;

import java.io.IOException;
import java.util.List;

public class Advent7 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent7().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int count = 0;
        for (String str : strs) {
            boolean inne = false;
            boolean abbaInne = false;
            boolean abbaUte = false;
            for (int i = 0; i < str.length() - 3; i++) {
                char c = str.charAt(i);
                if (str.charAt(i) == str.charAt(i + 3) && str.charAt(i + 1) == str.charAt(i + 2) && str.charAt(i + 1) != str.charAt(i)) {
                    if (inne) {
                        System.out.println("inne "+str.substring(i,i+4));
                        abbaInne = true;
                    } else {
                        System.out.println("ute "+str.substring(i,i+4));
                        abbaUte = true;
                    }
                }
                if (c == '[') {
                    inne = true;
                }
                if (c == ']') {
                    inne = false;
                }
            }
            System.out.println(abbaUte);
            System.out.println(abbaInne);
            System.out.println();
            if (abbaUte && !abbaInne) {
                count++;
            }
        }
        return count;
    }
}
