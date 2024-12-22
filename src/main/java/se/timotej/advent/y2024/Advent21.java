package se.timotej.advent.y2024;

import java.io.IOException;
import java.util.List;

public class Advent21 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent21().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    char[][] numpad = new char[][]{
            {'7', '8', '9'},
            {'4', '5', '6'},
            {'1', '2', '3'},
            {0, '0', 'A'}
    };
    char[][] dirpad = new char[][]{
            {0, '^', 'A'},
            {'<', 'v', '>'}
    };

    private long calc(List<String> strs) {
        long svar = 0;

        for (String str : strs) {
            String commands1 = getCommands(str, numpad);
            String commands2 = getCommands(commands1, dirpad);
            String commands3 = getCommands(commands2, dirpad);
            long length = commands3.length();
            long number = Util.findAllLongs(str).getFirst();
            svar += length * number;
        }

        return svar;
    }

    private String getCommands(String str, char[][] pad) {
        int maxy = pad.length;
        int maxx = pad[0].length;
        int sx = -1;
        int sy = -1;
        for (int y = 0; y < maxy; y++) {
            for (int x = 0; x < maxx; x++) {
                if (pad[y][x] == 'A') {
                    sx = x;
                    sy = y;
                }
            }
        }
        if (sx == -1 || sy == -1) {
            throw new RuntimeException();
        }
        StringBuilder response = new StringBuilder();
        for (int si = 0; si < str.length(); si++) {
            char c = str.charAt(si);
            int tx = -1;
            int ty = -1;
            for (int y = 0; y < maxy; y++) {
                for (int x = 0; x < maxx; x++) {
                    if (pad[y][x] == c) {
                        tx = x;
                        ty = y;
                    }
                }
            }
            if (tx == -1 || ty == -1) {
                throw new RuntimeException("Did not find " + c + " in pad");
            }
            if (pad[sy][0] == 0 && tx == 0) {
                while (sy > ty) {
                    sy--;
                    response.append("^");
                }
                while (sy < ty) {
                    sy++;
                    response.append("v");
                }
            }
            if (pad[ty][0] == 0 && sx == 0) {
                while (sx < tx) {
                    sx++;
                    response.append(">");
                }
            }
            while (sx > tx) {
                sx--;
                response.append("<");
            }
            while (sy < ty) {
                sy++;
                response.append("v");
            }
            while (sy > ty) {
                sy--;
                response.append("^");
            }
            while (sx < tx) {
                sx++;
                response.append(">");
            }
            response.append("A");
        }
        return response.toString();
    }
}
