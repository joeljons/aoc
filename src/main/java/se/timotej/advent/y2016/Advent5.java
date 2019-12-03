package se.timotej.advent.y2016;

import java.io.IOException;

public class Advent5 {

    public static void main(String[] args) throws IOException {
        String svar = new Advent5().calc(Online.get().get(0));
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private String calc(String str) {
        String pwd = "";
        for(int i=0;pwd.length()<8;i++){
            String md5 = Util.MD5(str + i);
            if(md5.startsWith("00000")){
                pwd += md5.charAt(5);
            }
        }
        return pwd;
    }
}