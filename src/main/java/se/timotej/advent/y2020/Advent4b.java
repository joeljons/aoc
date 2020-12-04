package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent4b {

    public static void main(String[] args) throws IOException {
        int svar = new Advent4b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private int calc(List<String> strs) {
        int sum = 0;
        boolean fel = false;
        Set<String> needed = new HashSet<>(Set.of("byr","iyr","eyr","hgt","hcl","ecl","pid"));
        for (String str : strs) {
            if (str.isEmpty()) {
                if (!fel && needed.isEmpty()) {
                    sum++;
                }
                fel = false;
                needed = new HashSet<>(Set.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"));
            } else {
                String[] keyvalue = str.split(" ");
                for (String s : keyvalue) {
                    String[] split = s.split(":");
                    String key = split[0];
                    String value = split[1];
                    needed.remove(key);

                    if (key.equals("byr")) {
                        try {
                            int i = Integer.parseInt(value);
                            if (i < 1920 || i > 2002) {
                                fel = true;
                            }
                        } catch (Exception e) {
                            fel = true;
                        }
                    }
                    if (key.equals("iyr")) {
                        try {
                            int i = Integer.parseInt(value);
                            if (i < 2010 || i > 2020) {
                                fel = true;
                            }
                        } catch (Exception e) {
                            fel = true;
                        }
                    }
                    if (key.equals("eyr")) {
                        try {
                            int i = Integer.parseInt(value);
                            if (i < 2020 || i > 2030) {
                                fel = true;
                            }
                        } catch (Exception e) {
                            fel = true;
                        }
                    }
                    if (key.equals("hgt")) {
                        if (value.endsWith("cm")) {
                            try {
                                int i = Integer.parseInt(value.substring(0, value.length() - 2));
                                if (i < 150 || i > 193) {
                                    fel = true;
                                }
                            } catch (Exception e) {
                                fel = true;
                            }
                        } else if (value.endsWith("in")) {
                            try {
                                int i = Integer.parseInt(value.substring(0, value.length() - 2));
                                if (i < 59 || i > 76) {
                                    fel = true;
                                }
                            } catch (Exception e) {
                                fel = true;
                            }
                        } else {
                            fel = true;
                        }
                    }
                    if (key.equals("hcl")) {
                        if (!value.matches("#[0-9a-f]{6}")) {
                            fel = true;
                        }
                    }
                    if (key.equals("ecl")) {
                        if (!Set.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(value)) {
                            fel = true;
                        }
                    }
                    if (key.equals("pid")) {
                        if (!value.matches("[0-9]{9}")) {
                            fel = true;
                        }
                    }
                }
            }

        }
        if (!fel && needed.isEmpty()) {
            sum++;
        }
        return sum;

    }

}
