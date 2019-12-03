package se.timotej.advent.y2016;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Advent23b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent23b().calc(Online.get(23));
        System.out.println("svar = " + svar);
//        Online.submit(svar);
    }

    int reg[] = new int[4];
    int pos;

    private int calc(List<String> strs) {
        reg[0] = 12;
        long count=0;
        String[][] lines = Util.stringMatrix(strs);
        while (pos < lines.length) {
            if(++count%10_000_000==0){
                System.out.println("count = " + count);
                System.out.println("reg = " + Arrays.toString(reg));
            }
            String[] line = lines[pos];
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
                    String[] fiddle = lines[togglePos];
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
                        throw new RuntimeException();
                    }
                    strs.set(togglePos, StringUtils.join(fiddle, " "));
                }
            } else {
                throw new RuntimeException();
            }
            pos++;
        }
        return reg[0];
    }

    private int getValue(String str) {
        return NumberUtils.isCreatable(str) ? Integer.parseInt(str) : reg[str.charAt(0) - 'a'];
    }

}