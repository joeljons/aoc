package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.List;

public class Advent12b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent12b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
//        Moon[] start = new Moon[]{
//                new Moon(17, 5, 1),
//                new Moon(-2, -8, 8),
//                new Moon(7, -6, 14),
//                new Moon(1, -10, 4)
//        };
//        Moon[] moons = new Moon[]{
//                new Moon(17, 5, 1),
//                new Moon(-2, -8, 8),
//                new Moon(7, -6, 14),
//                new Moon(1, -10, 4)
//        };
        Moon[][] start = new Moon[][]{
                {
                        new Moon(17),
                        new Moon(-2),
                        new Moon(7),
                        new Moon(1)
                },
                {
                        new Moon(5),
                        new Moon(-8),
                        new Moon(-6),
                        new Moon(-10)
                },
                {
                        new Moon(1),
                        new Moon(8),
                        new Moon(14),
                        new Moon(4)
                }
        };
        Moon[][] moons = new Moon[][]{
                {
                        new Moon(17),
                        new Moon(-2),
                        new Moon(7),
                        new Moon(1)
                },
                {
                        new Moon(5),
                        new Moon(-8),
                        new Moon(-6),
                        new Moon(-10)
                },
                {
                        new Moon(1),
                        new Moon(8),
                        new Moon(14),
                        new Moon(4)
                }
        };
        long[] cycles = new long[]{-1, -1, -1};
        for (long i = 1; cycles[0] == -1 || cycles[1] == -1 || cycles[2] == -1; i++) {
            for (int j = 0; j < 3; j++) {
                for (int ma = 0; ma < 4; ma++) {
                    for (int mb = ma + 1; mb < 4; mb++) {
                        if (moons[j][ma].x < moons[j][mb].x) {
                            moons[j][ma].dx++;
                            moons[j][mb].dx--;
                        } else if (moons[j][ma].x > moons[j][mb].x) {
                            moons[j][ma].dx--;
                            moons[j][mb].dx++;
                        }
                    }
                }
                boolean same = true;
                for (int ma = 0; ma < 4; ma++) {
                    moons[j][ma].x += moons[j][ma].dx;
                    if (moons[j][ma].x != start[j][ma].x || moons[j][ma].dx != start[j][ma].dx) {
                        same = false;
                    }
                }
                if (same) {
                    System.out.println("same j = " + j + ", i = " + i);
                    if (cycles[j] == -1) {
                        cycles[j] = i;
                    }
                }
            }
        }
        long svar = cycles[0] * cycles[1] / gcd(cycles[0], cycles[1]);
        return svar * cycles[2] / gcd(svar, cycles[2]);
    }

    private long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    private class Moon {
        int x;
        int dx;

        public Moon(int x) {
            this.x = x;
        }
    }
}