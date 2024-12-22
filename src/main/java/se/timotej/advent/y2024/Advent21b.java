package se.timotej.advent.y2024;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent21b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent21b().calc(Online.get());
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
            Map<String, Long> map = splitA(getCommands(str, numpad));
            for (int j = 0; j < 25; j++) {
                Map<String, Long> next = new HashMap<>();
                for (Map.Entry<String, Long> entry : map.entrySet()) {
                    Map<String, Long> subMap = splitA(getCommands(entry.getKey(), dirpad));
                    for (Map.Entry<String, Long> subEntry : subMap.entrySet()) {
                        next.merge(subEntry.getKey(), subEntry.getValue() * entry.getValue(), Long::sum);
                    }
                }
                map = next;
            }
            long length = map.entrySet().stream().mapToLong(e -> e.getKey().length() * e.getValue()).sum();
            long number = Util.findAllLongs(str).getFirst();
            svar += length * number;
        }

        return svar;
    }

    private Map<String, Long> splitA(String commands) {
        Map<String, Long> map = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < commands.length(); i++) {
            char c = commands.charAt(i);
            sb.append(c);
            if (c == 'A') {
                map.merge(sb.toString(), 1L, Long::sum);
                sb.setLength(0);
            }
        }
        if (!sb.isEmpty()) {
            throw new RuntimeException();
        }
        return map;
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
