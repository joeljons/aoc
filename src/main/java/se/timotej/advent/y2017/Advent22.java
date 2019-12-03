package se.timotej.advent.y2017;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent22 {
    public static void main(String[] args) throws IOException {
        //new Advent21().calc(Collections.singletonList("3"));
        new Advent22().calc(Online.get(22));
    }

    int[] dx = {0, 1, 0, -1};
    int[] dy = {-1, 0, 1, 0};

    private void calc(List<String> strs) {
        Map<Pair<Integer, Integer>, Boolean> g = new HashMap<>();
        int len = strs.size()/2;
        for(int y=-len;y<=len;y++){
            String row = strs.get(y + len);
            for (int i = 0; i < row.length(); i++) {
                char c = row.charAt(i);
                if(c=='#'){
                    g.put(Pair.of(y, i -len ), true);
                }

            }
        }
        int px=0;
        int py=0;
        int dir=0;
        int infected = 0;
        for(int tur=0;tur<10000;tur++){
            Pair<Integer, Integer> key = Pair.of(py, px);
            Boolean alreadyInf = g.getOrDefault(key, false);
            if(alreadyInf){
                dir = (dir+1)%4;
                //infected--;
                g.put(key, false);
            } else {
                dir = (dir+3)%4;
                infected++;
                g.put(key, true);
            }
            px += dx[dir];
            py += dy[dir];
        }
        System.out.println("infected = " + infected);
    }
}