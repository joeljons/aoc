package se.timotej.advent.y2018;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Advent17 {

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        String svar = new Advent17().calc(Online.get(17));
        long duration = System.currentTimeMillis()-start;
        System.out.println("svar = " + svar);
        System.out.println("duration = " + duration);
//        Online.submit(svar);
    }

    char[][] g = new char[2000][2000];
    int miny = 10000;
    int maxy = -1;
    int minx = 10000;
    int maxx = -1;
int nr = 0;
    private String calc(List<String> strs) throws FileNotFoundException {
        int svar = 0;

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
        rek(500, 0);
        int nr = 0;
        svar = calcSvar();
        //debug(0);
        return String.valueOf(svar);
    }

    int lastSvar = 0;

    private void rek(int startWx, int startWy) throws FileNotFoundException {
        boolean fallLeft, fallRight, rekd;
        /*if (nr++ % 1000 == 0) {
            int svar =calcSvar();
            System.out.println("svar = " + svar);
            if(nr/1000<1000) {
                int debug = debug(nr / 1000);
                System.out.println("debug = " + debug);
                System.out.println("nr = " + nr);
            }
        }*/
        do {
            rekd = false;
            int wy = startWy;
            int wx = startWx;
            if (!isFree(g[wy][wx])) {
                break;
            }
//            System.out.println("startWx = " + startWx);
//            System.out.println("startWy = " + startWy);
            int apa = 42;
            while (isFree(g[wy + 1][wx]) && wy <= maxy) {
                g[wy + 1][wx] = '|';
                wy++;
            }
            if (wy > maxy) {
                return;
            }
            //Vänster
            int x;
            for (x = wx; !isFree(g[wy + 1][x]) && isFree(g[wy][x]); x--) {

            }
            fallLeft = isFree(g[wy + 1][x]);
            int leftX = x;
            //Höger
            for (x = wx; !isFree(g[wy + 1][x]) && isFree(g[wy][x]); x++) {

            }
            fallRight = isFree(g[wy + 1][x]);
            int rightX = x;
            if (!fallLeft && !fallRight) {
                for (x = wx; isFree(g[wy][x]); x--) {
                    g[wy][x] = '~';
                }
                for (x = wx+1; isFree(g[wy][x]); x++) {
                    g[wy][x] = '~';
                }
            } else {
                for (x = wx; !isFree(g[wy + 1][x]) && isFree(g[wy][x]); x--) {
                    g[wy][x] = '|';
                }
                for (x = wx+1; !isFree(g[wy + 1][x]) && isFree(g[wy][x]); x++) {
                    g[wy][x] = '|';
                }
            }
            if (fallLeft) {
                g[wy][leftX] = '|';
                rek(leftX, wy);
                if (g[wy + 1][leftX] == '~') {
                    rekd = true;
                }
            }
            if (fallRight) {
                g[wy][rightX] = '|';
                rek(rightX, wy);
                if (g[wy + 1][rightX] == '~') {
                    rekd = true;
                }
            }
        } while ((!fallLeft && !fallRight) || rekd);
    }

    private int debug(int nr) throws FileNotFoundException {
        PrintWriter debug = new PrintWriter("ut" + nr + ".txt");
        int svar =0;
        for (int y = miny; y <= maxy; y++) {
            for (int x = minx - 10; x < maxx + 10; x++) {
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
        debug.close();
        return svar;
    }

    private int calcSvar() {
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