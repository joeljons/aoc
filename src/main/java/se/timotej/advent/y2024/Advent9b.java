package se.timotej.advent.y2024;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Advent9b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent9b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;

        String s = strs.get(0);
        List<Integer> disk = new ArrayList<>();
        int fileId = 0;
        for (int i = 0; i < s.length(); i++) {
            int len = s.charAt(i) - '0';
            for (int j = 0; j < len; j++) {
                disk.add(fileId);
            }
            fileId++;
            i++;
            if (i < s.length()) {
                int blankLen = s.charAt(i) - '0';
                for (int j = 0; j < blankLen; j++) {
                    disk.add(-1);
                }
            }
        }
        int j = disk.size() - 1;
        while (j >= 0) {
            while (j >= 0 && disk.get(j) == -1) {
                j--;
            }
            int diskId = disk.get(j);
            int diskLen = 0;
            while (j >= 0 && disk.get(j) == diskId) {
                j--;
                diskLen++;
            }
            for (int i = 0; i <= j; i++) {
                if (disk.get(i) == -1) {
                    int ledig = 0;
                    for (int k = i; k <= j && disk.get(k) == -1; k++) {
                        ledig++;
                    }
                    if (ledig >= diskLen) {
                        for (int k = 0; k < diskLen; k++) {
                            disk.set(i + k, diskId);
                            disk.set(j + 1 + k, -1);
                        }
                        break;
                    }
                }
            }
        }
        for (int i = 0; i < disk.size(); i++) {
            if (disk.get(i) != -1) {
                svar += disk.get(i) * i;
            }
        }

        return svar;
    }
}
