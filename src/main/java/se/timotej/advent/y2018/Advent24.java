package se.timotej.advent.y2018;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Advent24 {

    public static void main(String[] args) throws IOException {
        String svar = new Advent24().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    List<Group> groups = new ArrayList<>();

    private String calc(List<String> strs) {
        int svar = 0;
        int team = 0;
        for (String str : strs) {
            if (str.isBlank()) {
                team = 1;
                continue;
            }
            List<Integer> ints = Util.findAllInts(str);
            if (ints.isEmpty()) {
                continue;
            }
            Group group = new Group();
            group.team = team;
            group.units = ints.get(0);
            group.hpEach = ints.get(1);
            group.attack = ints.get(2);
            group.initiative = ints.get(3);
            Matcher matcher = Pattern.compile("(\\w+) damage").matcher(str);
            matcher.find();
            group.damage = matcher.group(1);

            matcher = Pattern.compile("immune to ([^;)]*)[;)]").matcher(str);
            if (matcher.find()) {
                group.immunes.addAll(Arrays.asList(matcher.group(1).split(", ")));
            }

            matcher = Pattern.compile("weak to ([^;)]*)[;)]").matcher(str);
            if (matcher.find()) {
                group.weaks.addAll(Arrays.asList(matcher.group(1).split(", ")));
            }
            if (str.contains("¤")) {
                group.name = str.split("¤")[1];
            }
            groups.add(group);
        }


        while (teamsAlive() > 1) {
            System.out.println("Select phase");
            for (Group group : groups) {
                group.chosen = false;
                if (group.units > 0) {
                    System.out.println(String.format("%s contains %d units", group.name, group.units));
                }
            }
            groups =
                    groups.stream().sorted(Comparator.comparing(Group::minusEffectivePower).thenComparing(Comparator.comparing(Group::minusInitiative))).collect(Collectors.toList());
            for (Group group : groups) {
                if (group.units > 0) {
                    group.selectTarget();
                }
            }
            groups = groups.stream().sorted(Comparator.comparing(Group::minusInitiative)).collect(Collectors.toList());
            System.out.println("Attack phase");
            for (Group group : groups) {
                if (group.units > 0 && group.target != null) {
                    group.doAttack();
                }
            }
            System.out.println("totalhp() = " + totalhp());
        }
        svar = totalhp();
        return String.valueOf(svar);
    }

    private int totalhp() {
        int svar = 0;
        for (Group group : groups) {
            svar += group.units;
        }
        return svar;
    }

    private int teamsAlive() {
        int team0 = 0;
        int team1 = 0;
        for (Group group : groups) {
            if (group.units > 0) {
                if (group.team == 0) {
                    team0 = 1;
                }
                if (group.team == 1) {
                    team1 = 1;
                }
            }
        }
        return team0 + team1;
    }

    private class Group {
        int units;
        int hpEach;
        int attack;
        String damage;
        int initiative;
        List<String> weaks = new ArrayList<>();
        List<String> immunes = new ArrayList<>();
        int team;
        Group target;
        String name;
        boolean chosen;

        public void selectTarget() {
            Group bestGroup = null;
            int bestDamage = 0;
            int bestEffectivePower = 0;
            int bestInitiative = 0;
            for (Group against : groups) {
                if (against.team == team) {
                    continue;
                }
                if (against.units == 0) {
                    continue;
                }
                if (against.chosen) {
                    continue;
                }
                int damage = attackDamage(against);
                if (damage == 0) {
                    continue;
                }
                System.out.println(String.format("%s would deal %s %d damage", name, against.name, damage));
                if (damage > bestDamage
                        || (damage == bestDamage
                        && (against.effectivePower() > bestEffectivePower
                        || (against.effectivePower() == bestEffectivePower && against.initiative > bestInitiative)))) {
                    bestGroup = against;
                    bestDamage = damage;
                    bestEffectivePower = against.effectivePower();
                    bestInitiative = against.initiative;
                }
            }
            target = bestGroup;
            if (target != null) {
                target.chosen = true;
            }
        }

        private int attackDamage(Group against) {
            if (against.immunes.contains(damage)) {
                return 0;
            }
            int dmg = effectivePower();
            if (against.weaks.contains(damage)) {
                dmg *= 2;
            }
            return dmg;
        }

        private int effectivePower() {
            return units * attack;
        }

        private int minusEffectivePower() {
            return -effectivePower();
        }

        public void doAttack() {
            int kills = attackDamage(target) / target.hpEach;
            if (kills > target.units) {
                kills = target.units;
            }
            System.out.println(String.format("%s attacks %s, killing %d units", name, target.name, kills));
            target.units -= kills;
        }

        public int minusInitiative() {
            return -initiative;
        }
    }
}

//Too low 25365