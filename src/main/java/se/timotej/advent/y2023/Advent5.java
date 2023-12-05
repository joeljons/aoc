package se.timotej.advent.y2023;

import java.io.IOException;
import java.util.List;

public class Advent5 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent5().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = Long.MAX_VALUE;
        List<List<String>> input = Util.splitByDoubleNewline(strs);
        for (Long seed : Util.findAllLongs(input.get(0).get(0))) {
            long now = seed;
            for (List<String> mapping : input.subList(1, input.size())) {
                for (String line : mapping.subList(1, mapping.size())) {
                    List<Long> m = Util.findAllLongs(line);
                    if (now >= m.get(1) && now < m.get(1) + m.get(2)){
                        now = now - m.get(1)+ m.get(0);
                        break;
                    }
                }
            }
            if(now<svar)svar = now;
        }
        return svar;
    }
}
