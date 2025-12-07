package se.timotej.advent.y2025;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Advent6 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent6().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;
        List<List<Long>> input = new ArrayList<>();
        for(int y=0;y<strs.size()-1;y++){
            input.add(Util.findAllPositiveLongs(strs.get(y)));
        }

        List<String> operators = Util.findAll(strs.getLast(), "\\S", Function.identity());
        for(int x=0;x<operators.size();x++){
            boolean plus = operators.get(x).equals("+");
            Long now = plus ? 0L : 1L;
            for(int y=0;y<input.size();y++){
                if(plus)
                    now += input.get(y).get(x);
                else
                    now *= input.get(y).get(x);
            }
            svar += now;
        }
        return svar;
    }
}
