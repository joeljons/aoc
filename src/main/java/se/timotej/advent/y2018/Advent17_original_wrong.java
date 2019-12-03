package se.timotej.advent.y2018;

import org.apache.commons.lang3.tuple.Pair;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Advent17_original_wrong {

    public static void main(String[] args) throws IOException {
        String svar = new Advent17_original_wrong().calc(Online.get());
        System.out.println("svar = " + svar);
//        Online.submit(svar);
    }

    char[][] g = new char[2000][2000];

    private String calc(List<String> strs) throws FileNotFoundException {
        int svar = 0;
        int miny = 10000;
        int maxy = -1;
        int minx = 10000;
        int maxx = -1;
        for (String str : strs) {
            List<Integer> line = Util.findAllInts(str);
            if (str.startsWith("x")) {
                int x = line.get(0);
                minx = Math.min(minx, line.get(0));
                maxx = Math.max(maxx, line.get(0));

                miny = Math.min(miny, line.get(1));
                maxy = Math.max(maxy, line.get(2));
                for (int y = line.get(1); y <= line.get(2); y++) {
                    g[y][x] = '#';
                }
            } else {
                int y = line.get(0);
                minx = Math.min(minx, line.get(1));
                maxx = Math.max(maxx, line.get(2));

                miny = Math.min(miny, line.get(0));
                maxy = Math.max(maxy, line.get(0));
                for (int x = line.get(1); x <= line.get(2); x++) {
                    g[y][x] = '#';
                }
            }
        }
        g[0][500] = '+';
        //PrintWriter debug = new PrintWriter("ut.txt");
        /*for (int y = 0; y <= maxy; y++) {
            for (int x = minx-10; x < maxx+10; x++) {
                char c = g[y][x];
                if (c == 0) {
                    debug.print(" ");
                }
                debug.print(c);
                if (c == '|' || c == '~') {
                    svar++;
                }
            }
            debug.println();
        }
        debug.close();*/
        System.out.println("miny = " + miny);
        System.out.println("maxy = " + maxy);
        boolean stuck;
        int stuckCount = 0;
        List<Pair<Integer, Integer>> kallor = new ArrayList<>();
        kallor.add(Pair.of(500, 0));
        int lastSvar;
        int nr=0;
        int svarSammCount = 0;
        do {
            lastSvar = svar;
            List<Pair<Integer, Integer>> nyaKallor = new ArrayList<>();
            List<Pair<Integer, Integer>> kallorToRemove = new ArrayList<>();
            for (Pair<Integer, Integer> kalla : kallor) {
                int wx = kalla.getLeft();
                int wy = kalla.getRight();
                boolean addedSomething = false;
                while (isFree(g[wy + 1][wx]) && wy <= maxy) {
                    g[wy + 1][wx] = '|';
                    wy++;
                }
                if (wy == maxy) {
                    break;
                }
                //Vänster
                int x;
                for (x = wx; !isFree(g[wy + 1][x]) && isFree(g[wy][x]); x--) {

                }
                boolean fallLeft = isFree(g[wy + 1][x]);
                if (fallLeft) {
                    nyaKallor.add(Pair.of(x, wy));
                    if(g[wy][x] == 0)addedSomething=true;
                    g[wy][x] = '|';
                }
                //Höger
                for (x = wx; !isFree(g[wy + 1][x]) && isFree(g[wy][x]); x++) {

                }
                boolean fallRight = isFree(g[wy + 1][x]);
                if (fallRight) {
                    nyaKallor.add(Pair.of(x, wy));
                    if(g[wy][x] == 0)addedSomething=true;
                    g[wy][x] = '|';
                }
                if (!fallLeft && !fallRight) {
                    for (x = wx; !isFree(g[wy + 1][x]) && isFree(g[wy][x]); x--) {
                        if(g[wy][x] == 0)addedSomething=true;
                        g[wy][x] = '~';
                    }
                    for (x = wx; !isFree(g[wy + 1][x]) && isFree(g[wy][x]); x++) {
                        if(g[wy][x] == 0)addedSomething=true;
                        g[wy][x] = '~';
                    }
                } else {
                    for (x = wx; !isFree(g[wy + 1][x]) && isFree(g[wy][x]); x--) {
                        if(g[wy][x] == 0)addedSomething=true;
                        g[wy][x] = '|';
                    }
                    for (x = wx; !isFree(g[wy + 1][x]) && isFree(g[wy][x]); x++) {
                        if(g[wy][x] == 0)addedSomething=true;
                        g[wy][x] = '|';
                    }
                }
                if(!addedSomething){
                    kallorToRemove.add(kalla);
                }
            }
            for (Pair<Integer, Integer> integerIntegerPair : nyaKallor) {
                if (!kallor.contains(integerIntegerPair)) {
                    kallor.add(integerIntegerPair);
                    lastSvar = 0;
                }
            }
            kallor.removeAll(kallorToRemove);
            svar = calcSvar(miny, maxy, minx, maxx);
            System.out.println("lastSvar = " + lastSvar);
            System.out.println("svar = " + svar);
            System.out.println();

            if(svar != lastSvar){
                svarSammCount=0;
            } else {
                svarSammCount++;
            }
        } while (svarSammCount<100);
        debug(miny, maxy, minx, maxx, nr++);
        return String.valueOf(svar);
    }

    private void debug(int miny, int maxy, int minx, int maxx, int nr) throws FileNotFoundException {
        PrintWriter debug = new PrintWriter("ut"+nr+".txt");
        for (int y = miny; y <= maxy; y++) {
            for (int x = minx - 10; x < maxx + 10; x++) {
                char c = g[y][x];
                if (c == 0) {
                    debug.print(" ");
                }
                debug.print(c);
            }
            debug.println();
        }
        debug.close();
    }

    private int calcSvar(int miny, int maxy, int minx, int maxx) {
        int svar = 0;
        for (int y = miny; y <= maxy; y++) {
            for (int x = minx - 10; x < maxx + 10; x++) {
                char c = g[y][x];
                if (c == '|' || c == '~') {
                    svar++;
                }
            }

        }
        return svar;
    }

    private boolean isFree(char c) {
        return c != '#' && c != '~';
    }

}

// fel 27109
// fel 47991
// fel 39217