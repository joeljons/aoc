package se.timotej.advent.y2016;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Advent14b {

    public static void main(String[] args) throws IOException, ExecutionException {
        int svar = new Advent14b().calc(Online.get(14).get(0));
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(String str) throws ExecutionException {
        LoadingCache<Integer, Pair<Integer, Character>> cache = CacheBuilder.newBuilder()
                .maximumSize(1000000)
                .build(CacheLoader.from(i -> longestRepeat(MD52016(str + i))));
        int keysFound = 0;
        int index = 0;
        for (index = 0; keysFound < 64; index++) {
            Pair<Integer, Character> nu = cache.get(index);
            if (nu.getKey() >= 3) {
                for (int i = 1; i <= 1000; i++) {
                    Pair<Integer, Character> next = cache.get(index + i);
                    if (next.getKey() >= 5 && next.getValue() == nu.getValue()) {
                        keysFound++;
                        System.out.println("keysFound = " + keysFound);
                        System.out.println("index = " + index);
                        break;
                    }
                }
            }
        }
        return index-1;
    }

    private Pair<Integer, Character> longestRepeat(String str) {
        char lastC = 0;
        int len = 0;
        int longest = 0;
        char firstThree = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == lastC) {
                len++;
                if(len == 3 && firstThree==0){
                    firstThree = c;
                }
            } else {
                longest = Math.max(longest, len);
                len = 1;
                lastC = c;
            }
        }
        return Pair.of(Math.max(longest, len), firstThree);
    }

    public String MD52016(String md5) {
        for(int i=0;i<=2016;i++){
            md5 = MD5(md5);
        }
        return md5;
    }

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }


}