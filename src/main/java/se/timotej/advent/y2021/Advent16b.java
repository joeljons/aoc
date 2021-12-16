package se.timotej.advent.y2021;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Advent16b {

    public static void main(String[] args) throws IOException {
        var svar = new Advent16b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
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
        Pair<Integer, Long> result = rek(binary, 0);
        return result.getRight();
    }

    private Pair<Integer, Long> rek(String str, int startPos) {
        int pos = startPos;
        int version = Integer.parseInt(str.substring(pos, pos + 3), 2);
        pos += 3;
        int typeId = Integer.parseInt(str.substring(pos, pos + 3), 2);
        pos += 3;
        long value = 0;
        if (typeId == 4) {//literal
            int nextP = pos;
            String binary = "";
            while (str.charAt(nextP) == '1') {
                binary += str.substring(nextP + 1, nextP + 5);
                nextP += 5;
            }
            binary += str.substring(nextP + 1, nextP + 5);
            nextP += 5;
            pos = nextP;
            value = Long.parseLong(binary, 2);
        } else { // operator
            int lengthTypeId = Integer.parseInt(str.substring(pos, pos + 1), 2);
            pos++;
            List<Long> parameters = new ArrayList<>();
            if (lengthTypeId == 0) { //total length bits
                int totalLength = Integer.parseInt(str.substring(pos, pos + 15), 2);
                pos += 15;
                int nextP = pos;
                while (nextP - pos < totalLength) {
                    Pair<Integer, Long> result = rek(str, nextP);
                    nextP = result.getLeft();
                    parameters.add(result.getRight());
                }
                pos = nextP;
            } else { // number of sub-packets immediately contained
                int numberOfSubPackets = Integer.parseInt(str.substring(pos, pos + 11), 2);
                pos += 11;
                for (int i = 0; i < numberOfSubPackets; i++) {
                    Pair<Integer, Long> result = rek(str, pos);
                    pos = result.getLeft();
                    parameters.add(result.getRight());
                }
            }
            if (typeId == 0) {
                for (Long parameter : parameters) {
                    value += parameter;
                }
            } else if (typeId == 1) {
                value = 1;
                for (Long parameter : parameters) {
                    value *= parameter;
                }
            } else if (typeId == 2) {
                value = parameters.get(0);
                for (Long parameter : parameters) {
                    value = Math.min(value, parameter);
                }
            } else if (typeId == 3) {
                value = parameters.get(0);
                for (Long parameter : parameters) {
                    value = Math.max(value, parameter);
                }
            } else if (typeId == 5) {
                value = parameters.get(0) > parameters.get(1) ? 1 : 0;
            } else if (typeId == 6) {
                value = parameters.get(0) < parameters.get(1) ? 1 : 0;
            } else if (typeId == 7) {
                value = parameters.get(0).equals(parameters.get(1)) ? 1 : 0;
            }
        }
        return Pair.of(pos, value);
    }

}
