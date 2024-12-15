package se.timotej.advent.y2024;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Advent15b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent15b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;

        List<List<String>> input = Util.splitByDoubleNewline(strs);
        char[][] g = Util.charMatrix(input.get(0).stream().map(Advent15b::transform).collect(Collectors.toList()));
        int maxy = g.length;
        int maxx = g[0].length;
        int sx = -1;
        int sy = -1;
        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                if (g[y][x] == '@') {
                    sx = x;
                    sy = y;
                    g[y][x] = '.';
                }
            }
        }
        for (String line : input.get(1)) {
            for (char c : line.toCharArray()) {
                int dx = 0;
                int dy = 0;
                if (c == '<') {
                    dx = -1;
                } else if (c == '>') {
                    dx = 1;
                } else if (c == '^') {
                    dy = -1;
                } else if (c == 'v') {
                    dy = 1;
                }
                if (dy == 0) {
                    int nx = sx + dx;
                    int ny = sy + dy;
                    if (g[ny][nx] == '.') {
                        sx = nx;
                        sy = ny;
                    } else if (g[ny][nx] == '[' || g[ny][nx] == ']') {
                        int ox = nx;
                        int oy = ny;
                        while (g[oy][ox] == '[' || g[oy][ox] == ']') {
                            ox += dx;
                            oy += dy;
                        }
                        if (g[oy][ox] == '.') {
                            while (ox != nx) {
                                g[oy][ox] = g[oy][ox - dx];
                                ox -= dx;
                            }
                            g[ny][nx] = '.';
                            sx = nx;
                            sy = ny;
                        }
                    } else if (g[ny][nx] != '#') {
                        throw new RuntimeException();
                    }
                } else {
                    boolean[] rowmark = new boolean[maxx];
                    rowmark[sx] = true;
                    boolean moved = moveit(g, rowmark, sy, dy);
                    if (moved) {
                        sy += dy;
                    }
                }
            }
        }

        debug(maxy, maxx, sx, sy, g);

        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                if (g[y][x] == '[') {
                    svar += 100 * y + x;
                }
            }
        }

        return svar;
    }

    private boolean moveit(char[][] g, boolean[] rowmark, int y, int dy) {
        boolean[] nextrow = new boolean[rowmark.length];
        int ny = y + dy;
        boolean pressure = false;
        for (int x = 0; x < g[0].length; x++) {
            if (rowmark[x]) {
                if (g[ny][x] == '#') {
                    return false;
                } else if (g[ny][x] == '[') {
                    nextrow[x] = true;
                    nextrow[x + 1] = true;
                    pressure = true;
                } else if (g[ny][x] == ']') {
                    nextrow[x] = true;
                    nextrow[x - 1] = true;
                    pressure = true;
                }
            }
        }
        boolean moved = !pressure || moveit(g, nextrow, ny, dy);
        if (moved) {
            for (int x = 0; x < g[0].length; x++) {
                if (rowmark[x]) {
                    g[ny][x] = g[y][x];
                    g[y][x] = '.';
                }
            }
        }
        return moved;
    }

    private static String transform(String line) {
        StringBuilder sb = new StringBuilder();
        for (char c : line.toCharArray()) {
            sb.append(switch (c) {
                case '#' -> "##";
                case 'O' -> "[]";
                case '.' -> "..";
                case '@' -> "@.";
                default -> throw new RuntimeException();
            });
        }
        return sb.toString();
    }

    private static void debug(int maxy, int maxx, int sx, int sy, char[][] g) {
        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                if (x == sx && y == sy) {
                    System.out.print('@');
                } else {
                    System.out.print(g[y][x]);
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
//1159099 too low