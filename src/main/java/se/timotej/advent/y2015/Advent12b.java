package se.timotej.advent.y2015;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Advent12b {
    public static void main(String[] args) throws IOException {
        int svar = new Advent12b().calc(Online.get(12).get(0));
        System.out.println("svar = " + svar);
        Online.submit(12, 2, svar);
    }

    private int calc(String str) {
        Gson gson = new Gson();
        Object o = gson.fromJson(str, Object.class);
        return count(o);
    }

    private int count(Object o) {
        if (o instanceof ArrayList) {
            int sum = 0;
            for (Object o1 : ((ArrayList) o)) {
                sum += count(o1);
            }
            return sum;
        } else if (o instanceof String) {
            return 0;
        } else if (o instanceof Map) {
            int sum = 0;
            for (Object entry : ((Map) o).entrySet()) {
                Map.Entry e = (Map.Entry) entry;
                if ("red".equals(e.getValue())) {
                     return 0;
                }
                sum += count(e.getKey()) + count(e.getValue());
            }
            return sum;
        } else if (o instanceof Double) {
            return ((Double) o).intValue();
        } else {
            throw new RuntimeException("fattar inte " + o.getClass());
        }
    }

}