package se.timotej.advent.y2017;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent18 {
    public static void main(String[] args) throws IOException {
        new Advent18().calc(Online.get(18));
    }

    private void calc(List<String> strs) {
        int i = 0;
        Map<String, Long> g = new HashMap<>();
        long lastPlayed = -1;
        while (i < strs.size()) {
            Instr instr = getInstr(strs, i);
            if (instr.wat.equals("set")) {
                long setval;
                if (instr.ystr != null) {
                    setval = g.getOrDefault(instr.ystr, 0L);
                } else {
                    setval = instr.y;
                }
                g.put(instr.x, setval);
            } else if (instr.wat.equals("add")) {
                long addval;
                if (instr.ystr != null) {
                    addval = g.getOrDefault(instr.ystr, 0L);
                } else {
                    addval = instr.y;
                }
                g.put(instr.x, g.getOrDefault(instr.x, 0L) + addval);
            } else if (instr.wat.equals("mul")) {
                long mulval;
                if (instr.ystr != null) {
                    mulval = g.getOrDefault(instr.ystr, 0L);
                } else {
                    mulval = instr.y;
                }
                g.put(instr.x, g.getOrDefault(instr.x, 0L) * mulval);
            }else if (instr.wat.equals("mod")) {
                long modval;
                if (instr.ystr != null) {
                    modval = g.getOrDefault(instr.ystr, 0L);
                } else {
                    modval = instr.y;
                }
                g.put(instr.x, g.getOrDefault(instr.x, 0L) % modval);
            } else if (instr.wat.equals("jgz")) {
                if (instr.x.equals("1") || g.getOrDefault(instr.x, 0L) > 0L) {
                    i += instr.y - 1;
                }
            } else if (instr.wat.equals("rcv")) {
                if (instr.x.equals("1") || g.getOrDefault(instr.x, 0L) != 0L) {
                    Online.submit(18, 1, String.valueOf(lastPlayed));
                    System.exit(0);
                }
            } else if (instr.wat.equals("snd")) {
                lastPlayed = g.getOrDefault(instr.x,0L);
            } else {
                throw new RuntimeException();
            }
            i++;
        }
    }

    private Map<Integer, Instr> instrMap = new HashMap<>();

    private Instr getInstr(List<String> strs, int i) {
        Instr instr = instrMap.get(i);
        if (instr == null) {
            String str = strs.get(i);
            String[] split = str.split(" ");
            instr = new Instr(split[0], split[1], split.length == 3 ? split[2] : null);
            instrMap.put(i, instr);
        }
        return instr;
    }

    private class Instr {
        String wat;
        String x;
        long y;
        String ystr;

        public Instr(String wat, String x, String yy) {
            this.wat = wat;
            this.x = x;
            if (yy != null) {
                if (Character.isAlphabetic(yy.charAt(0))) {
                    ystr = yy;
                } else {
                    this.y = Long.parseLong(yy);
                }
            }
        }
    }
}