package se.timotej.advent.y2023;

import java.io.IOException;
import java.util.List;

public class Advent13 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent13().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;
        for (List<String> g : Util.splitByDoubleNewline(strs)) {
            svar += getScore(g);
        }
        return svar;
    }

    private long getScore(List<String> g) {
        int maxx = g.get(0).length();
        int maxy = g.size();
        for(int ref=0;ref<maxx-1;ref++){
            boolean ok=true;
            for(int x0=ref,x1=ref+1;x0>=0&&x1<maxx;x0--,x1++){
                for(int y=0;y<maxy;y++){
                    if(g.get(y).charAt(x0) != g.get(y).charAt(x1)){
                        ok=false;
                        break;
                    }
                }
                if(!ok)break;
            }
            if(ok)return ref+1;
        }
        for(int ref=0;ref<maxy-1;ref++){
            boolean ok=true;
            for(int y0=ref,y1=ref+1;y0>=0&&y1<maxy;y0--,y1++){
                for(int x=0;x<maxx;x++){
                    if(g.get(y0).charAt(x) != g.get(y1).charAt(x)){
                        ok=false;
                        break;
                    }
                }
                if(!ok)break;
            }
            if(ok)return 100*(ref+1);
        }
        return 0;
    }
}
