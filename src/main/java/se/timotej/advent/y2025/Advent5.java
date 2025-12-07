package se.timotej.advent.y2025;

import java.io.IOException;
import java.util.List;

public class Advent5 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent5().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;
        List<List<String>> input = Util.splitByDoubleNewline(strs);
        for (String idStr : input.get(1)) {
            Long id = Long.valueOf(idStr);
            for (String range : input.get(0)) {
                List<Long> myRange = Util.findAllPositiveLongs(range);
                if(myRange.getFirst() <= id && id <= myRange.getLast()) {
                    svar++;
                    break;
                }
            }
        }
        return svar;
    }
}
