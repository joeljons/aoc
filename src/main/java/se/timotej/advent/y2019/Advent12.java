package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.List;

public class Advent12 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent12().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int svar = 0;
        Moon[] moons = new Moon[]{
                new Moon(17, 5, 1),
                new Moon(-2, -8, 8),
                new Moon(7, -6, 14),
                new Moon(1, -10, 4)

        };
        for (int i = 0; i < 1000; i++) {
            for (int ma = 0; ma < 4; ma++) {
                for (int mb = ma + 1; mb < 4; mb++) {
                    if (moons[ma].x < moons[mb].x) {
                        moons[ma].dx++;
                        moons[mb].dx--;
                    } else if (moons[ma].x > moons[mb].x) {
                        moons[ma].dx--;
                        moons[mb].dx++;
                    }
                    if (moons[ma].y < moons[mb].y) {
                        moons[ma].dy++;
                        moons[mb].dy--;
                    } else if (moons[ma].y > moons[mb].y) {
                        moons[ma].dy--;
                        moons[mb].dy++;
                    }
                    if (moons[ma].z < moons[mb].z) {
                        moons[ma].dz++;
                        moons[mb].dz--;
                    } else if (moons[ma].z > moons[mb].z) {
                        moons[ma].dz--;
                        moons[mb].dz++;
                    }
                }
            }
            for (int ma = 0; ma < 4; ma++) {
                moons[ma].x += moons[ma].dx;
                moons[ma].y += moons[ma].dy;
                moons[ma].z += moons[ma].dz;
            }
            int apa = 42;
        }
        for (int ma = 0; ma < 4; ma++) {
            int a = Math.abs(moons[ma].x);
            a += Math.abs(moons[ma].y);
            a += Math.abs(moons[ma].z);
            int b = Math.abs(moons[ma].dx);
            b += Math.abs(moons[ma].dy);
            b += Math.abs(moons[ma].dz);
            svar += a * b;
        }
        return svar;
    }

    private class Moon {
        int x;
        int y;
        int z;
        int dx, dy, dz;

        public Moon(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public String toString() {
            return "Moon{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    ", dx=" + dx +
                    ", dy=" + dy +
                    ", dz=" + dz +
                    '}';
        }
    }
}