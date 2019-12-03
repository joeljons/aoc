package se.timotej.advent.y2017;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent23 {
    public static void main(String[] args) throws IOException {
        //new Advent21().calc(Collections.singletonList("3"));
        new Advent23().calc(Online.get(23));
    }

    private void calc(List<String> strs) {
        int i = 0;
        int svar = 0;
        Map<String, Long> g = new HashMap<>();
        int cnt = 0;
        while (i < strs.size()) {
            cnt++;
//            if (cnt % 1000000 == 0) {
                System.out.println("cnt = " + cnt);
                System.out.println("i = " + i);
                System.out.println("svar = " + svar);
//            }
            Instr instr = getInstr(strs, i);
            if (instr.wat.equals("set")) {
                long setval;
                if (instr.ystr != null) {
                    setval = g.getOrDefault(instr.ystr, 0L);
                } else {
                    setval = instr.y;
                }
                g.put(instr.x, setval);
            } else if (instr.wat.equals("sub")) {
                long subval;
                if (instr.ystr != null) {
                    subval = g.getOrDefault(instr.ystr, 0L);
                } else {
                    subval = instr.y;
                }
                g.put(instr.x, g.getOrDefault(instr.x, 0L) - subval);
            } else if (instr.wat.equals("mul")) {
                long mulval;
                if (instr.ystr != null) {
                    mulval = g.getOrDefault(instr.ystr, 0L);
                } else {
                    mulval = instr.y;
                }
                g.put(instr.x, g.getOrDefault(instr.x, 0L) * mulval);
                svar++;
            } else if (instr.wat.equals("jnz")) {
                if (instr.x.equals("1") || g.getOrDefault(instr.x, 0L) != 0L) {
                    i += instr.y - 1;
                }
            } else {
                throw new RuntimeException();
            }
            i++;
        }

        System.out.println("svar = " + svar);
        Online.submit(23, 1, svar);
    }

    private Map<Integer, Instr> instrMap = new HashMap<>();

    private Instr getInstr(List<String> strs, int i) {
        Instr instr = instrMap.get(i);
        if (instr == null) {
            String str = strs.get(i);
            String[] split = str.split(" ");
            instr = new Instr(split[0], split[1], split[2]);
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
            if (Character.isAlphabetic(yy.charAt(0))) {
                ystr = yy;
            } else {
                this.y = Long.parseLong(yy);
            }
        }
    }
}