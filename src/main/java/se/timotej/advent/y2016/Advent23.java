package se.timotej.advent.y2016;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.util.List;

public class Advent23 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent23().calc(Online.get());
        System.out.println("svar = " + svar);
        //Online.submit(svar);
    }

    int reg[] = new int[4];
    int pos;

    private int calc(List<String> strs) {
        reg[0] = 7;
        while (pos < strs.size()) {
//            System.out.println("pos = " + pos);
//            System.out.println("reg = " + Arrays.toString(reg));
            String str = strs.get(pos);
            String[] line = str.split(" ");
            if (line[0].equals("cpy")) {
                int val = getValue(line[1]);
                if ('a' <= line[2].charAt(0) && line[2].charAt(0) <= 'd') {
                    reg[line[2].charAt(0) - 'a'] = val;
                }
            } else if (line[0].equals("inc")) {
                if ('a' <= line[1].charAt(0) && line[1].charAt(0) <= 'd') {
                    reg[line[1].charAt(0) - 'a']++;
                }
            } else if (line[0].equals("dec")) {
                if ('a' <= line[1].charAt(0) && line[1].charAt(0) <= 'd') {
                    reg[line[1].charAt(0) - 'a']--;
                }
            } else if (line[0].equals("jnz")) {
                if (getValue(line[1]) != 0) {
                    pos += getValue(line[2]) - 1;
                }
            } else if (line[0].equals("tgl")) {
                int togglePos = pos + getValue(line[1]);
                if (0 <= togglePos && togglePos < strs.size()) {
                    String[] fiddle = strs.get(togglePos).split(" ");
                    if (fiddle[0].equals("cpy")) {
                        fiddle[0] = "jnz";
                    } else if (fiddle[0].equals("inc")) {
                        fiddle[0] = "dec";
                    } else if (fiddle[0].equals("dec")) {
                        fiddle[0] = "inc";
                    } else if (fiddle[0].equals("jnz")) {
                        fiddle[0] = "cpy";
                    } else if (fiddle[0].equals("tgl")) {
                        fiddle[0] = "inc";
                    } else {
                        throw new RuntimeException(strs.get(togglePos));
                    }
                    strs.set(togglePos, StringUtils.join(fiddle, " "));
                }
            } else {
                throw new RuntimeException(str);
            }
            pos++;
        }
        return reg[0];
    }

    private int getValue(String str) {
        return NumberUtils.isCreatable(str) ? Integer.parseInt(str) : reg[str.charAt(0) - 'a'];
    }

}