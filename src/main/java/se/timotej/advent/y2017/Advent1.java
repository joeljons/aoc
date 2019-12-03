package se.timotej.advent.y2017;

import java.io.IOException;

public class Advent1 {

    public static void main(String[] args) throws IOException {
        advent1("1122");
        advent1("1111");
        advent1("1234");
        advent1("91212129");
        advent1(Online.get(1).get(0));

        advent1b("1212");
        advent1b("1221");
        advent1b("123425");
        advent1b("123123");
        advent1b("12131415");
        advent1b(Online.get(1).get(0));
    }

    public static void advent1(String s) {
        int sum = 0;
        char last = s.charAt(s.length() - 1);
        for (int i = 0; i < s.length(); i++) {
            char nu = s.charAt(i);
            if (nu == last) {
                sum += Character.digit(nu, 10);
            }
            last = nu;
        }
        System.out.println("sum = " + sum);
    }

    public static void advent1b(String s) {
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            char last = s.charAt((i + s.length() / 2) % s.length());
            char nu = s.charAt(i);
            if (nu == last) {
                sum += Character.digit(nu, 10);
            }
            last = nu;
        }
        System.out.println("sum = " + sum);
    }
}
