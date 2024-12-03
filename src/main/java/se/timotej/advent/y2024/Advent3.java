package se.timotej.advent.y2024;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Advent3 {

    public static void main(String[] args) throws IOException {
        int svar = new Advent3().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int svar = 0;
        Pattern pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
        for (String str : strs) {
            Matcher matcher = pattern.matcher(str);
            while (matcher.find()) {
                svar += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
            }
        }
        return svar;
    }
}
