package se.timotej.advent.y2015;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Advent24 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent24().calc(Online.get());
        System.out.println("svar = " + String.valueOf(svar));
//        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        int[] vikter = Util.intArray(strs);
        int sum = 0;
        for (int vikt : vikter) {
            sum += vikt;
        }
        System.out.println("sum = " + sum);
        Boolean[] grupp1 = new Boolean[vikter.length];
        Arrays.fill(grupp1, false);
        int ettor = 0;
        long bestQE = Integer.MAX_VALUE;
        int bestCount = Integer.MAX_VALUE;
        while (bestCount == Integer.MAX_VALUE && !grupp1[grupp1.length - 1]) {
            ettor++;
            System.out.println("ettor = " + ettor);
            grupp1[grupp1.length - 1] = true;
            Arrays.sort(grupp1);
            int fall = 0;
            do {
                int delsum = 0;
                int count = 0;
                long qe = 1;
                for (int i = 0; i < vikter.length; i++) {
                    if (grupp1[i]) {
                        int vikt = vikter[i];
                        count++;
                        qe *= vikt;
                        if (qe < 0) {
                            break;
                        }
                        delsum += vikt;
                    }
                }
                if (qe > 0 && delsum == sum / 3 && (count < bestCount || count == bestCount && qe < bestQE)) {
                    boolean g[] = new boolean[sum / 3 + 1];
                    g[0] = true;
                    for (int i = 0; i < vikter.length; i++) {
                        int vikt = vikter[i];
                        if (!grupp1[i]) {
                            for (int j = g.length - 1 - vikt; j >= 0; j--) {
                                if (g[j]) {
                                    g[j + vikt] = true;
                                }
                            }
                        }
                    }
                    if (g[sum / 3]) {
                        bestCount = count;
                        bestQE = qe;
                        System.out.println("bestCount = " + bestCount);
                        System.out.println("bestQE = " + bestQE);

                        long qe2 = 1;
                        for (int i = 0; i < vikter.length; i++) {
                            if (grupp1[i]) {
                                int vikt = vikter[i];
                                qe2 *= vikt;
                                System.out.println("vikt = " + vikt);
                                System.out.println("qe2 = " + qe2);
                            }
                        }
                    }
                    fall++;
                }
            } while (Util.nextPermutation(grupp1));
            System.out.println("fall = " + fall);
        }
        return bestQE;
    }

}
