package se.timotej.advent.y2023;

import java.io.IOException;
import java.util.*;

public class Advent19b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent19b().calc(Online.get(0));
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;
        List<List<String>> inputs = Util.splitByDoubleNewline(strs);
        Map<String, Rule> rules = new HashMap<>();
        Map<String, SortedSet<Integer>> g = new HashMap<>();
        g.put("x", new TreeSet<>());
        g.put("m", new TreeSet<>());
        g.put("a", new TreeSet<>());
        g.put("s", new TreeSet<>());
        for (String s : inputs.get(0)) {
            String[] split = s.split("[{},]");
            Rule r = new Rule();
            r.defaultDest = split[split.length - 1];
            for (int i = 1; i < split.length - 1; i++) {
                String[] split2 = split[i].split("[<>:]");
                int cmp = Integer.parseInt(split2[1]);
                boolean greater = split[i].contains(">");
                g.get(split2[0]).add(greater ? cmp : cmp - 1);
                r.conditions.add(new Condition(split2[0], greater, cmp, split2[2]));
            }
            rules.put(split[0], r);
        }
        SortedSet<Integer> xs = g.get("x");
        SortedSet<Integer> ms = g.get("m");
        SortedSet<Integer> as = g.get("a");
        SortedSet<Integer> ss = g.get("s");
        xs.add(4000);
        ms.add(4000);
        as.add(4000);
        ss.add(4000);
        Map<String, Integer> variables = new HashMap<>();
        int lastx = 0;
        for (Integer x : xs) {
            System.out.println("x = " + x);
            variables.put("x", x);
            int lastm = 0;
            for (Integer m : ms) {
                variables.put("m", m);
                int lasta = 0;
                for (Integer a : as) {
                    variables.put("a", a);
                    int lasts = 0;
                    for (Integer s : ss) {
                        variables.put("s", s);
                        String now = "in";
                        while (!now.equals("A") && !now.equals("R")) {
                            Rule r = rules.get(now);
                            now = r.evaluate(variables);
                        }
                        if (now.equals("A")) {
                            svar += (long) (x - lastx) * (long) (m - lastm) * (long) (a - lasta) * (long) (s - lasts);
                        }
                        lasts = s;
                    }
                    lasta = a;
                }
                lastm = m;
            }
            lastx = x;
        }
        return svar;
    }

    private static class Rule {
        List<Condition> conditions = new ArrayList<>();
        String defaultDest;

        public String evaluate(Map<String, Integer> variables) {
            for (Condition condition : conditions) {
                if ((condition.greater && variables.get(condition.variable) > condition.cmp)
                        || (!condition.greater && variables.get(condition.variable) < condition.cmp)) {
                    return condition.destination;
                }
            }
            return defaultDest;
        }
    }

    private static class Condition {
        String variable;
        boolean greater;
        private final int cmp;
        String destination;

        public Condition(String variable, boolean greater, int cmp, String destination) {
            this.variable = variable;
            this.greater = greater;
            this.cmp = cmp;
            this.destination = destination;
        }
    }
}
