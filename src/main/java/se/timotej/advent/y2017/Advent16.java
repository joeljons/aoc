package se.timotej.advent.y2017;

import java.io.IOException;
import java.util.List;

public class Advent16 {
    public static void main(String[] args) throws IOException {
        new Advent16().calc(Online.get(16));
    }

    private void calc(List<String> strs) {
        String str = strs.get(0);
        String[] split = str.split(",");
        char g[] = new char[16];
        for (int i = 0; i < 16; i++) {
            g[i] = (char) ('a' + i);
        }
        for (String instr : split) {
            if (instr.charAt(0) == 's') {
                int num = Integer.parseInt(instr.substring(1));
                rotate(g, num);
                /*for (int i = 0; i < num; i++) {
                    char tmp = g[15];
                    for (int j = 15; j>0 ;j--) {
                        g[j] = g[j - 1];
                    }
                    g[0] = tmp;
                }*/
            } else if (instr.charAt(0) == 'x') {
                String[] s1 = instr.substring(1).split("/");
                int a = Integer.parseInt(s1[0]);
                int b = Integer.parseInt(s1[1]);
                char tmp = g[a];
                g[a] = g[b];
                g[b] = tmp;
            } else if (instr.charAt(0) == 'p') {
                String[] s1 = instr.substring(1).split("/");
                int p1 = -1;
                int p2 = -1;
                for (int i = 0; i < 16; i++) {
                    if (g[i] == s1[0].charAt(0)) {
                        p1 = i;
                    }
                    if (g[i] == s1[1].charAt(0)) {
                        p2 = i;
                    }
                }
                char tmp = g[p1];
                g[p1] = g[p2];
                g[p2] = tmp;
            } else {
                throw new RuntimeException(instr);
            }
        }
        System.out.println(new String(g));
        /*int swapper[] = new int[16];
        for (int i = 0; i < 16; i++) {
            swapper[g[i]-'a'] = i;
        }
        char G[][] = new char[2][16];
        G[0] = g;
        int lastto=0;
        for(int it=1;it<=1000000000;it++){
            if (it % 1000000==0) {
                System.out.println("it = " + it/1000000);
            }
            int from = (it+1)%2;
            int to = (it)%2;
            lastto = to;
            for(int i=0;i<16;i++){
                G[to][swapper[i]] = G[from][i];
            }
        }
        System.out.println(new String(G[lastto]));*/
    }
    private static void rotate(char[] arr, int order) {
        if (arr == null || order < 0) {
            throw new IllegalArgumentException("The array must be non-null and the order must be non-negative");
        }
        int offset = arr.length - order % arr.length;
        if (offset > 0) {
            char[] copy = arr.clone();
            for (int i = 0; i < arr.length; ++i) {
                int j = (i + offset) % arr.length;
                arr[i] = copy[j];
            }
        }
    }
}