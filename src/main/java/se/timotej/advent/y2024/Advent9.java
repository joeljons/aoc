package se.timotej.advent.y2024;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Advent9 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent9().calc(Online.get());
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
        int i = 0;
        int j = disk.size() - 1;
        while (i < j) {
            while (disk.get(i) != -1) {
                i++;
            }
            if(j<=i)break;
            while (disk.get(j) == -1) {
                j--;
            }
            if(j<=i)break;
            disk.set(i, disk.get(j));
            disk.set(j, -1);
        }
        for(i=0;i<disk.size();i++){
            if(disk.get(i) != -1){
                svar += disk.get(i) * i;
            }
        }

        return svar;
    }
}
