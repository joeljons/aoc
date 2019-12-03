package se.timotej.advent.y2018;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent2b_v2 {

    public static void main(String[] args) throws IOException {
        List<String> strs = Online.get(2);
        for (int i = 0; i < 5_000_000; i++) {
            strs.add(RandomStringUtils.randomAlphabetic(26));
        }
        Lists.reverse(strs);
        List<StringWrapper> strWraps = new ArrayList<>(strs.size());
        for (String str : strs) {
            strWraps.add(new StringWrapper(str, 0));
        }
        long start = System.nanoTime();
        String svar = new Advent2b_v2().calc(strWraps);
        long duration = System.nanoTime() - start;
        System.out.println(duration / 1000000);
        System.out.println("svar = " + svar);
        //Online.submit(2, 2, svar);
    }

    private String calc(List<StringWrapper> strWraps) {
        int len = strWraps.get(0).value.length;
        System.out.println("APA");
        for (int p = 0; p < len; p++) {
            System.out.println("p = " + p);
            Set<StringWrapper> g = new HashSet<>();
            for (StringWrapper strWrap : strWraps) {
                strWrap.setCutPosition(p);
                if (!g.add(strWrap)) {
                    StringBuilder sb = new StringBuilder();
                    for (int p2 = 0; p2 < len; p2++) {
                        if (p != p2) {
                            sb.append(strWrap.value[p2]);
                        }
                    }
                    return sb.toString();
                }
            }
        }
        return null;
    }

    private static class StringWrapper {
        private final char value[];
        private int cutPosition;
        private int hash;

        public StringWrapper(String s, int cutPosition) {
            this.value = s.toCharArray();
            this.cutPosition = cutPosition;
        }

        public void setCutPosition(int cutPosition) {
            this.hash = 0;
            this.cutPosition = cutPosition;
        }

        @Override
        public boolean equals(Object anObject) {
            if (this == anObject) {
                return true;
            }
            if (anObject instanceof StringWrapper) {
                StringWrapper anotherString = (StringWrapper) anObject;
                int n = value.length;
                if (n == anotherString.value.length) {
                    char v1[] = value;
                    char v2[] = anotherString.value;
                    int i = 0;
                    while (n-- != 0) {
                        if (i != cutPosition && v1[i] != v2[i]) {
                            return false;
                        }
                        i++;
                    }
                    return true;
                }
            }
            return false;
        }

        @Override
        public int hashCode() {
            int h = hash;
            if (h == 0 && value.length > 0) {
                char val[] = value;

                for (int i = 0; i < value.length; i++) {
                    if (i != cutPosition) {
                        h = 31 * h + val[i];
                    }
                }
                hash = h;
            }
            return h;
        }
    }

}
