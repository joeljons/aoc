package se.timotej.advent.y2016;

import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Advent21 {

    public static void main(String[] args) throws IOException, ExecutionException {
        String svar = new Advent21().calc(Online.get());
        System.out.println("svar = " + svar);
        //Online.submit(String.valueOf(svar));
    }

    char[] pwd = "abcdefgh".toCharArray();
    //char[] pwd = "abcde".toCharArray();

    private String calc(List<String> strs) {
        for (String str : strs) {
            String[] line = str.split(" ");
            if (str.startsWith("swap position")) {
                int x = Integer.parseInt(line[2]);
                int y = Integer.parseInt(line[5]);
                swap(x, y);
            } else if (str.startsWith("swap letter")) {
                int x = ArrayUtils.indexOf(pwd, line[2].charAt(0));
                int y = ArrayUtils.indexOf(pwd, line[5].charAt(0));
                swap(x, y);
            } else if (str.startsWith("rotate left")) {
                int x = Integer.parseInt(line[2]);
                rotate(-x);
            } else if (str.startsWith("rotate right")) {
                int x = Integer.parseInt(line[2]);
                rotate(x);
            } else if (str.startsWith("rotate based on position of letter")) {
                int x = ArrayUtils.indexOf(pwd, line[6].charAt(0));
                if (x >= 4) {
                    x++;
                }
                rotate(x + 1);
            } else if (str.startsWith("reverse positions")) {
                int x = Integer.parseInt(line[2]);
                int y = Integer.parseInt(line[4]);
                for (int i = 0; i <= (y - x) / 2; i++) {
                    swap(x + i, y - i);
                }
            } else if (str.startsWith("move position")) {
                int x = Integer.parseInt(line[2]);
                int y = Integer.parseInt(line[5]);
                char c = pwd[x];
                for (int i = x; i < pwd.length - 1; i++) {
                    pwd[i] = pwd[i + 1];
                }
                for (int i = pwd.length - 1; i > y; i--) {
                    pwd[i] = pwd[i - 1];
                }
                pwd[y] = c;
            } else {
                throw new RuntimeException(str);
            }
            //System.out.println("      01234567");
            //System.out.println("str = " + str);
            //System.out.println("pwd = " + new String(pwd));
        }
        return new String(pwd);
    }

    char[] tmp = new char[pwd.length];

    private void rotate(int x) {
        for (int i = 0; i < pwd.length; i++) {
            tmp[(x + pwd.length + i) % pwd.length] = pwd[i];
        }
        System.arraycopy(tmp, 0, pwd, 0, pwd.length);
    }

    private void swap(int x, int y) {
        char tmp = pwd[x];
        pwd[x] = pwd[y];
        pwd[y] = tmp;
    }

}

//fel ahcdfbge