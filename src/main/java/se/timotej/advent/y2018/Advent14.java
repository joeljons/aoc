package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

public class Advent14 {

    public static void main(String[] args) throws IOException {
        String svar = new Advent14().calc(Online.get());
        System.out.println("svar = " + svar);
//        Online.submit(svar);
    }

    private String calc(List<String> strs) {
        List<Integer> g = new ArrayList<>();
        int p1 = 0;
        int p2 = 1;
        g.add(3);
        g.add(7);
        int input = 556061;
        while (g.size() <= input + 10) {
            int next = g.get(p1) + g.get(p2);
            if (next >= 10) {
                g.add(1);
            }
            g.add(next % 10);
//            System.out.println("g = " + g);
            p1 = (p1 + g.get(p1) + 1) % g.size();
            p2 = (p2 + g.get(p2) + 1) % g.size();
        }
        return g.subList(input, input + 10).stream().collect(
                Collector.of(StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append,
                        StringBuilder::toString
                )
        );
/*        String svar = "";
        for (int i = input; i < input + 10; i++) {
            svar += g.get(i);
        }
        return svar;*/
    }
}
// fel 3342501481
// fel 9761316401
// fel 9761136410