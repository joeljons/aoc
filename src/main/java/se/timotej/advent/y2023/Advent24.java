package se.timotej.advent.y2023;

import java.io.IOException;
import java.util.List;

public class Advent24 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent24().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    double testmin = 200000000000000L;
    double testmax = 400000000000000L;

    private long calc(List<String> strs) {
        long svar = 0;
        for (int i = 0; i < strs.size(); i++) {
            List<Long> allLongs0 = Util.findAllLongs(strs.get(i));
            long px0 = allLongs0.get(0);
            long py0 = allLongs0.get(1);
            long vx0 = allLongs0.get(3);
            long vy0 = allLongs0.get(4);
            for (int j = i + 1; j < strs.size(); j++) {
                List<Long> allLongs1 = Util.findAllLongs(strs.get(j));
                long px1 = allLongs1.get(0);
                long py1 = allLongs1.get(1);
                long vx1 = allLongs1.get(3);
                long vy1 = allLongs1.get(4);

                if (intersect(px0, py0,
                        px0 + vx0, py0 + vy0,
                        px1, py1,
                        px1 + vx1, py1 + vy1)) {
                    if (inside(xCross) && inside(yCross)) {
                        if ((xCross - px0) / vx0 > 0 && (xCross - px1) / vx1 > 0) {
                            svar++;
                        }
                    }
                }
            }
        }
        return svar;
    }

    double xCross, yCross;

    private boolean intersect(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
        double ua, denom = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);
        if (denom == 0) {
            return false;
        }
        ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / denom;
        xCross = x1 + ua * (x2 - x1);
        yCross = y1 + ua * (y2 - y1);
        return true;
    }

    private boolean inside(double pos) {
        return testmin <= pos && pos <= testmax;
    }
}
