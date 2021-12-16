package se.timotej.advent.y2021;

import java.io.IOException;
import java.util.List;

public class Advent16 {

    public static void main(String[] args) throws IOException {
        var svar = new Advent16().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    int sum = 0;

    private int calc(List<String> strs) {
        String str = strs.get(0);
        String binary = "";
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            int num = Integer.parseInt(c + "", 16);
            String b = Integer.toBinaryString(num);
            while (b.length() < 4) {
                b = "0" + b;
            }
            binary += b;
        }
        rek(binary, 0);
        return sum;
    }

    private int rek(String str, int startPos) {
        int pos = startPos;
        int version = Integer.parseInt(str.substring(pos, pos + 3), 2);
        sum += version;
        pos += 3;
        int typeId = Integer.parseInt(str.substring(pos, pos + 3), 2);
        pos += 3;
        if (typeId == 4) {//literal
            int nextP = pos;
            while (str.charAt(nextP) == '1') {
                nextP += 5;
            }
            nextP += 5;
            pos = nextP;
        } else { // operator
            int lengthTypeId = Integer.parseInt(str.substring(pos, pos + 1), 2);
            pos++;
            if (lengthTypeId == 0) { //total length bits
                int totalLength = Integer.parseInt(str.substring(pos, pos + 15), 2);
                pos += 15;
                int nextP = pos;
                while (nextP - pos < totalLength) {
                    nextP = rek(str, nextP);
                }
                pos = nextP;
            } else { // number of sub-packets immediately contained
                int numberOfSubPackets = Integer.parseInt(str.substring(pos, pos + 11), 2);
                pos += 11;
                for (int i = 0; i < numberOfSubPackets; i++) {
                    pos = rek(str, pos);
                }
            }
        }
        return pos;
    }

}
