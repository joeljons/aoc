package se.timotej.advent.y2016;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ExecutionException;

public class Advent17 {

    public static void main(String[] args) throws IOException, ExecutionException {
        String svar = new Advent17().calc(Online.get().get(0));
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dD = new int[]{-1, 1, 0, 0};
    int[] dR = new int[]{0, 0, -1, 1};

    private String calc(String str) {
        Queue<String> q = new ArrayDeque<>();
        q.add(str);
        while (!q.isEmpty()) {
            String nu = q.remove();
            int D = 0;
            int R = 0;
            for (int i = 0; i < nu.length(); i++) {
                char c = nu.charAt(i);
                if (c == 'D') {
                    D++;
                }
                if (c == 'U') {
                    D--;
                }
                if (c == 'R') {
                    R++;
                }
                if (c == 'L') {
                    R--;
                }
            }
            String md5 = Util.MD5(nu);
            for (int i = 0; i < "UDLR".length(); i++) {
                char c = "UDLR".charAt(i);
                int nyD = D + dD[i];
                int nyR = R + dR[i];
                if (nyD >= 0 && nyD < 4 && nyR >= 0 && nyR < 4) {
                    char mi = md5.charAt(i);
                    if (mi >= 'b') {
                        String next = nu + c;
                        if (nyD == 3 && nyR == 3) {
                            return next.substring(str.length(), next.length());
                        }
                        q.add(next);
                    }
                }
            }
        }
        throw new RuntimeException("No answer found");
    }

}