package se.timotej.advent.y2016;

import java.io.IOException;

public class Advent5b {

    public static void main(String[] args) throws IOException {
        String svar = new Advent5b().calc(Online.get().get(0));
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private String calc(String str) {
        char[] pwd = new char[8];
        int filled=0;
        for(int i=0;filled<8;i++){
            String md5 = Util.MD5(str + i);
            if(md5.startsWith("00000")){
                if(md5.charAt(5)<'8' && pwd[md5.charAt(5)-'0'] == 0){
                    pwd[md5.charAt(5) - '0'] = md5.charAt(6);
                    filled++;
                    System.out.println("filled = " + filled);
                }
            }
        }
        return new String(pwd);
    }
}