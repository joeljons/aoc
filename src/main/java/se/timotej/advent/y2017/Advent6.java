package se.timotej.advent.y2017;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent6 {
    public static void main(String[] args) throws IOException {
        new Advent6().calc(Arrays.asList("0\t2\t7\t0"));
        new Advent6().calc(Online.get(6));
    }

    private void calc(List<String> str) {
        int[] ints = Util.intArray(str.get(0));
        List<Integer> state = new ArrayList<>();
        for (int anInt : ints) {
            state.add(anInt);
        }
        Map<List<Integer>, Integer> g = new HashMap<>();
        int count = 0;
        while (!g.containsKey(state)) {
            g.put(new ArrayList<>(state), count);
            int max=-1;
            int maxPos=-1;
            for(int i=0;i<ints.length;i++){
                if(state.get(i) > max ){
                    max = state.get(i);
                    maxPos = i;
                }
            }
            state.set(maxPos, 0);
            while(max > 0){
                maxPos = (maxPos+1)%ints.length;
                max--;
                state.set(maxPos, state.get(maxPos) + 1);
            }

            count++;
        }
        System.out.println("A = " + count);
        System.out.println("B = " + (count-g.get(state)));
    }


}
