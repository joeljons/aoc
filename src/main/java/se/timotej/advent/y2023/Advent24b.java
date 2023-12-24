package se.timotej.advent.y2023;

import java.io.IOException;
import java.util.List;

public class Advent24b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent24b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        System.out.println("Solve[{");
        for (int i = 0; i < 3; i++) {
            List<Long> allLongs0 = Util.findAllLongs(strs.get(i));
            long px0 = allLongs0.get(0);
            long py0 = allLongs0.get(1);
            long pz0 = allLongs0.get(2);
            long vx0 = allLongs0.get(3);
            long vy0 = allLongs0.get(4);
            long vz0 = allLongs0.get(5);

            //System.out.printf("t%d>=0,%n", i);
            System.out.printf("%d+%d*t%d==a+d*t%d,%n", px0, vx0, i, i);
            System.out.printf("%d+%d*t%d==b+e*t%d,%n", py0, vy0, i, i);
            System.out.printf("%d+%d*t%d==c+f*t%d%s%n", pz0, vz0, i, i, i == 2 ? "" : ",");

        }
        System.out.println("},{a,b,c,d,e,f,t0,t1,t2}]");
        long a = 291669802654110L;
        long b = 103597826800230L;
        long c = 251542427650413L;
        return a + b + c;
    }
}
