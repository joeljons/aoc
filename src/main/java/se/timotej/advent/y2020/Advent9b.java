package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Advent9b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent9b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        LinkedList<Long> last25 = new LinkedList<>();
        List<Long> alla = new ArrayList<>();
        for (String str : strs) {
            long now = Long.parseLong(str);
            if (last25.size() >= 25) {
                boolean funkar = false;
                for (int i = 0; i < last25.size(); i++) {
                    for (int j = i + 1; j < last25.size(); j++) {
                        if (last25.get(i) + (last25.get(j)) == (now)) {
                            funkar = true;
                        }
                    }
                }
                if (!funkar) {
                    for (int i = 0; i < alla.size(); i++) {
                        long sum = 0;
                        long smallest = alla.get(i);
                        long largest = alla.get(i);
                        for (int j = i; j < alla.size(); j++) {
                            sum += alla.get(j);
                            smallest = Math.min(smallest, alla.get(j));
                            largest = Math.max(largest, alla.get(j));
                            if (sum == now) {
                                return smallest + largest;
                            }
                        }
                    }
                }
            }
            last25.add(now);
            alla.add(now);
        }
        return 0;
    }
}
