package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Advent19b_apa3 {

    public static void main(String[] args) throws IOException {
        String svar = new Advent19b_apa3().calc(Online.get(19));
        System.out.println("svar = " + svar);
//        Online.submit(19,2,svar);
    }

    int a=0;
    int b=0;
    int c=0;
    int d=0;
    int e=0;
    int f=0;
    private String calc(List<String> strs) {
        int ip = 2;

        List<Runnable> prog = new ArrayList<>();
        prog.add(() -> c = 16);
        prog.add(() -> e = 1);
        prog.add(() -> f = 1);
        prog.add(() -> b = e * f);
        prog.add(() -> b = b == d ? 1 : 0);
        prog.add(() -> c = b + c);
        prog.add(() -> c = c + 1);
        prog.add(() -> a = e + a);
        prog.add(() -> f++);
        prog.add(() -> b = f > d ? 1 : 0);
        prog.add(() -> c += b);
        prog.add(() -> c = 2);
        prog.add(() -> e = e + 1);
        prog.add(() -> b = e > d ? 1 : 0);
        prog.add(() -> c = b + c);
        prog.add(() -> c = 1);
        prog.add(() -> c = 1000);
        prog.add(() -> d = d + 2);
        prog.add(() -> d = d * d);
        prog.add(() -> d = c * d);
        prog.add(() -> d = d * 11);
        prog.add(() -> b = b + 6);
        prog.add(() -> b = b * c);
        prog.add(() -> b = b + 6);
        prog.add(() -> d = d + b);
        prog.add(() -> c = c + a);
        prog.add(() -> c = 0);
        prog.add(() -> b = c);
        prog.add(() -> b = b * c);
        prog.add(() -> b = c + b);
        prog.add(() -> b = c * b);
        prog.add(() -> b = b * 14);
        prog.add(() -> b = b * c);
        prog.add(() -> d = d + b);
        prog.add(() -> a = 0);
        prog.add(() -> c = 0);


        a = 1;
        int steps = 0;
        while (c >= 0 && c < prog.size()) {
            prog.get(c).run();
            c++;
            steps++;
            if (steps % 1000000 == 0) {
                System.out.println(String.format("%d\t%d\t%d\t%d\t%d\t%d",a,b,c,d,e,f));
            }

        }
        System.out.println("steps = " + steps);
        return String.valueOf(a);
    }

}


/*
prog.add(() -> c = c + 16);
        prog.add(() -> e = 1);
        prog.add(() -> f = 1);
        prog.add(() -> b = e * f);
        prog.add(() -> b = b == d ? 1 : 0);
        prog.add(() -> c = b + c);
        prog.add(() -> c = c + 1);
        prog.add(() -> a = e + a);
        prog.add(() -> f = f + 1);
        prog.add(() -> b = f > d ? 1 : 0);
        prog.add(() -> c = c + b);
        prog.add(() -> c = 2);
        prog.add(() -> e = e + 1);
        prog.add(() -> b = e > d ? 1 : 0);
        prog.add(() -> c = b + c);
        prog.add(() -> c = 1);
        prog.add(() -> c = 1000);
        prog.add(() -> d = d + 2);
        prog.add(() -> d = d * d);
        prog.add(() -> d = c * d);
        prog.add(() -> d = d * 11);
        prog.add(() -> b = b + 6);
        prog.add(() -> b = b * c);
        prog.add(() -> b = b + 6);
        prog.add(() -> d = d + b);
        prog.add(() -> c = c + a);
        prog.add(() -> c = 0);
        prog.add(() -> b = c);
        prog.add(() -> b = b * c);
        prog.add(() -> b = c + b);
        prog.add(() -> b = c * b);
        prog.add(() -> b = b * 14);
        prog.add(() -> b = b * c);
        prog.add(() -> d = d + b);
        prog.add(() -> a = 0);
        prog.add(() -> c = 0);
 */