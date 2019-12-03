package se.timotej.advent.y2016;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ExecutionException;

public class Advent17b {

    public static void main(String[] args) throws IOException, ExecutionException {
        int svar = new Advent17b().calc(Online.get().get(0));
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int[] dD = new int[]{-1, 1, 0, 0};
    int[] dR = new int[]{0, 0, -1, 1};

    private int calc(String str) {
        Queue<String> q = new ArrayDeque<>();
        q.add(str);
        int longest = 0;
        int count = 0;
        while (!q.isEmpty()) {
            if (count++ % 10000 == 0) {
                System.out.println("q.size() = " + q.size());
            }
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
                            int len = next.length() - str.length();
                            if (len > longest) {
                                longest = len;
                                System.out.println("longest = " + longest);
                            }
                        } else {
                            q.add(next);
                        }
                    }
                }
            }
        }
        return longest;
    }

}