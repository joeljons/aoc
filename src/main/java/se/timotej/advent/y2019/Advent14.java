package se.timotej.advent.y2019;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Advent14 {

    public static void main(String[] args) throws IOException, InterruptedException {
        long start = System.nanoTime();
        long svar = new Advent14().calc(Online.get());
        long duration = (System.nanoTime() - start) / 1000000;
        System.out.println("duration = " + duration);
        System.out.println("svar = " + svar);
         Online.submit(svar);
    }

    private long calc(List<String> strs) {
        Map<String, Reaction> reactions = new HashMap<>();

        for (String str : strs) {
            Reaction r = new Reaction(str);
            reactions.put(r.result.name, r);
        }

        Map<String, Long> inventory = new HashMap<>();
        inventory.put("FUEL", 1L);

        Map<String, Long> extra = new HashMap<>();

        boolean reacted;
        do {
            reacted = false;
            for (String name : new HashSet<>(inventory.keySet())) {
                Reaction reaction = reactions.get(name);
                if (reaction != null) {
                    long needed = inventory.remove(name) - extra.getOrDefault(name, 0L);
                    extra.remove(name);
                    long reactionMultiple = (needed + reaction.result.antal - 1) / reaction.result.antal;
                    long totalMade = reaction.result.antal * reactionMultiple;
                    long extraMade = totalMade - needed;
                    extra.put(name, extraMade + extra.getOrDefault(name, 0L));
                    for (Chemical chemical : reaction.source) {
                        inventory.put(chemical.name, chemical.antal * reactionMultiple + inventory.getOrDefault(chemical.name, 0L));
                    }
                    reacted = true;
                }

            }
        } while (reacted);


        return inventory.get("ORE");
    }

    private class Reaction {
        List<Chemical> source;
        Chemical result;

        public Reaction(String line) {
            String[] split = line.split(" => ");
            result = new Chemical(split[1]);
            split = split[0].split(", ");
            source = Arrays.stream(split).map(Chemical::new).collect(Collectors.toList());
        }

        @Override
        public String toString() {
            return "Reaction{" +
                    "source=" + source +
                    ", result=" + result +
                    '}';
        }
    }

    public static class Chemical {
        long antal;
        String name;

        @Override
        public String toString() {
            return "Chemical{" +
                    "antal=" + antal +
                    ", name='" + name + '\'' +
                    '}';
        }

        public Chemical(String whatAndName) {
            String[] split = whatAndName.split(" ");
            antal = Long.parseLong(split[0]);
            this.name = split[1];
        }
    }
}
