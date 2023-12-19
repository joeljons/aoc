package se.timotej.advent.y2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent19 {

    public static void main(String[] args) throws IOException {
        long svar = new Advent19().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        long svar = 0;
        List<List<String>> inputs = Util.splitByDoubleNewline(strs);
        Map<String, Rule> rules = new HashMap<>();
        for (String s : inputs.get(0)) {
            String[] split = s.split("[{},]");
            Rule r = new Rule();
            r.defaultDest = split[split.length - 1];
            for (int i = 1; i < split.length - 1; i++) {
                String[] split2 = split[i].split("[<>:]");
                r.conditions.add(new Condition(split2[0], split[i].contains(">"), Integer.parseInt(split2[1]), split2[2]));
            }
            rules.put(split[0], r);
        }
        for (String s : inputs.get(1)) {
            String[] split = s.substring(1, s.length() - 1).split("[=,]");
            Map<String, Integer> variables = new HashMap<>();
            for (int i = 0; i < split.length; i += 2) {
                variables.put(split[i], Integer.parseInt(split[i + 1]));
            }
            String now = "in";
            while (!now.equals("A") && !now.equals("R")) {
                Rule r = rules.get(now);
                now = r.evaluate(variables);
            }
            if (now.equals("A")) {
                for (Integer value : variables.values()) {
                    svar += value;
                }
            }
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
