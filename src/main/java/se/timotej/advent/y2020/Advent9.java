package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Advent9 {

    public static void main(String[] args) throws IOException {
        String svar = new Advent9().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private String calc(List<String> strs) {
        LinkedList<Long> last25 = new LinkedList<>();
        for (String str : strs) {
            long now = Long.parseLong(str);
            if (last25.size() >= 25) {
                boolean funkar = false;
                for (int i = 0; i < last25.size(); i++) {
                    for (int j = i + 1; j < last25.size(); j++) {
                        if (last25.get(i) + (last25.get(j)) == (now)) {
                            funkar = true;
                            break;
                        }
                    }
                }
                if (!funkar) {
                    return str;
                }
                last25.remove(0);
            }
            last25.add(now);
        }
        return "";
    }
}
