package se.timotej.advent.y2015;

import java.io.IOException;
import java.util.List;

public class Advent4b {
    public static void main(String[] args) throws IOException {
        new Advent4b().calc(Online.get(4));
    }

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    private void calc(List<String> strs) {
        String str = strs.get(0);
        for(int i=0;true;i++){
            String s = str + i;
            String md5 = MD5(s);
            if(md5.startsWith("000000")){
                System.out.println("i = " + i);
                Online.submit(4, 2, i);
                break;
            }
        }
    }

}
