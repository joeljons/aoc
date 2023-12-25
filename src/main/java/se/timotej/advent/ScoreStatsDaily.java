package se.timotej.advent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ScoreStatsDaily {

    private static final String ME = "Joel Jonsson";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("scores_2023.txt"));
        String str;
        Pattern pattern = Pattern.compile("(...)\\) Dec (\\d\\d)  \\d\\d:\\d\\d:\\d\\d  (.+)");
        int lastDay = 1;
        int lastScore = 0;
        int dayCount = 1;
        Map<String, Integer> scores = new HashMap<>();
        while ((str = br.readLine()) != null) {
            Matcher matcher = pattern.matcher(str);
            if (matcher.matches()) {
                int placement = Integer.parseInt(matcher.group(1).trim());
                int score = 101 - placement;
                int day = Integer.parseInt(matcher.group(2).trim());
                String name = matcher.group(3);
                if (name.endsWith(" (Sponsor)")) {
                    name = name.substring(0, name.length() - 10);
                }
                if (name.endsWith(" (AoC++)")) {
                    name = name.substring(0, name.length() - 8);
                }
                if (day != lastDay) {
                    dayCount++;
                    printMyPlacement(lastDay, scores, lastScore);
                    lastDay = day;
                    lastScore = scores.getOrDefault(ME, -1);
                }
                scores.put(name, scores.getOrDefault(name, 0) + score);
            }
        }
        printMyPlacement(lastDay, scores, lastScore);

        List<Map.Entry<String, Integer>> sortedList = toSorted(scores);
        lastScore = 0;
        int lastPlace = 0;
        System.out.println("PLACE\tSCORE\tAVG_PLACE\tNAME");
        for (int i = 0; i < sortedList.size(); i++) {
            Map.Entry<String, Integer> entry = sortedList.get(i);
            if (entry.getValue() != lastScore) {
                lastScore = entry.getValue();
                lastPlace = i + 1;
            }
            System.out.println(String.format("%4d:\t%4d\t%4.1f\t%s", lastPlace, entry.getValue(),
                    101 - (entry.getValue() / 2.0 / dayCount), entry.getKey()));
        }
    }

    private static List<Map.Entry<String, Integer>> toSorted(Map<String, Integer> scores) {
        return scores.entrySet().stream().sorted(Collections.<Map.Entry<String,
                Integer>>reverseOrder(Map.Entry.comparingByValue()).thenComparing(entry -> entry.getKey().equals(
                ME) ? 0 : 1))
                .collect(Collectors.toList());
    }

    private static void printMyPlacement(int day, Map<String, Integer> scores, int lastScore) {
        List<Map.Entry<String, Integer>> sortedList = toSorted(scores);
        for (int i = 0; i < sortedList.size(); i++) {
            Map.Entry<String, Integer> entry = sortedList.get(i);
            if (entry.getKey().equals(ME)) {
                System.out.println(String.format("Day: %2d  Score: %4d (%4s)  Place: %2d", day, entry.getValue(),
                        "+" + (entry.getValue() - Math.max(0, lastScore)), i + 1));
                return;
            }
        }
        System.out.println(String.format("Day: %2d  Score: %3d (%4s)  Place: %2d", day, 0,
                "+" + (0), sortedList.size() + 1));
    }
}
