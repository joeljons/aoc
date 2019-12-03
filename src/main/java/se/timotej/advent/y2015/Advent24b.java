package se.timotej.advent.y2015;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Advent24b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent24b().calc(Online.get());
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
                if (qe > 0 && delsum == sum / 4 && (count < bestCount || count == bestCount && qe < bestQE)) {
                    Boolean[] grupp2 = new Boolean[vikter.length];
                    Arrays.fill(grupp2, false);
                    for (int size2 = 1; size2 <= 10; size2++) {
                        grupp2[grupp1.length - 1] = true;
                        Arrays.sort(grupp2);
                        do {
                            int delsum2 = 0;
                            for (int i = 0; i < vikter.length; i++) {
                                if (grupp2[i]) {
                                    if (grupp1[i]) {
                                        delsum2 = -1;
                                        break;
                                    }
                                    int vikt = vikter[i];
                                    delsum2 += vikt;
                                }
                            }
                            if(delsum2 == sum/4){
                                boolean g[] = new boolean[sum / 4 + 1];
                                g[0] = true;
                                for (int i = 0; i < vikter.length; i++) {
                                    int vikt = vikter[i];
                                    if (!grupp1[i] && !grupp2[i]) {
                                        for (int j = g.length - 1 - vikt; j >= 0; j--) {
                                            if (g[j]) {
                                                g[j + vikt] = true;
                                            }
                                        }
                                    }
                                }
                                if (g[sum / 4]) {
                                    bestCount = count;
                                    bestQE = qe;
                                    System.out.println("bestCount = " + bestCount);
                                    System.out.println("bestQE = " + bestQE);
                                    size2 = 99;
                                    break;
                                }
                            }
                        } while (Util.nextPermutation(grupp2));
                    }
                    fall++;
                }
            } while (Util.nextPermutation(grupp1));
            System.out.println("fall = " + fall);
        }
        return bestQE;
    }

}
