package se.timotej.advent;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ScoreStats {

    private static final ZoneId CET = ZoneId.of("CET");
    public static final String LEADERBOARD = "187443";

    public static void main(String[] args) throws IOException {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

        Map<String, Map<LocalDate, Integer>> starsPerDay = new TreeMap<>();
        Map<String, Map<LocalDate, Integer>> solveTimeForLevel2 = new TreeMap<>();
        Map<Pair<LocalDate, Integer>, List<Pair<Integer, String>>> fastestPerProblem = new TreeMap<>();
        for (int year = 2015; year <= 2019; year++) {
            Stats stats = gson.fromJson(get(year), Stats.class);
            for (Member member : stats.members.values()) {
                for (Map.Entry<Integer, CompletionDay> integerCompletionDayEntry :
                        member.completionDayLevel.entrySet()) {
                    int day = integerCompletionDayEntry.getKey();
                    CompletionDay completionDay = integerCompletionDayEntry.getValue();
                    addToStarsPerDay(starsPerDay.computeIfAbsent(member.name, k -> new TreeMap<>()),
                            completionDay.level1);
                    addToStarsPerDay(starsPerDay.computeIfAbsent(member.name, k -> new TreeMap<>()),
                            completionDay.level2);
                    if (completionDay.level1 != null && completionDay.level2 != null) {
                        solveTimeForLevel2.computeIfAbsent(member.name, k -> new TreeMap<>()).put(LocalDate.of(year,
                                12, day),
                                completionDay.level2.getStarTs - completionDay.level1.getStarTs);
                    }
                    addToFastestPerProblem(fastestPerProblem, Pair.of(LocalDate.of(year, 12, day), 1),
                            completionDay.level1, member.name);
                    addToFastestPerProblem(fastestPerProblem, Pair.of(LocalDate.of(year, 12, day), 2),
                            completionDay.level2, member.name);
                }
            }
        }
        for (Map.Entry<String, Map<LocalDate, Integer>> outerEntry : starsPerDay.entrySet()) {
            System.out.println();
            System.out.println(String.format("Stars earned per day for %s:", outerEntry.getKey()));
            for (Map.Entry<LocalDate, Integer> entry : outerEntry.getValue().entrySet()) {
                System.out.println(String.format("%s %d", entry.getKey(), entry.getValue()));
            }
        }
        for (Map.Entry<String, Map<LocalDate, Integer>> outerEntry : solveTimeForLevel2.entrySet()) {
            System.out.println();
            System.out.println(String.format("Solve time for level2 for %s:", outerEntry.getKey()));
            for (Map.Entry<LocalDate, Integer> entry : outerEntry.getValue().entrySet()) {
                System.out.println(String.format("%s %s", entry.getKey(), formatDuration(entry.getValue())));
            }
        }
        System.out.println();
        System.out.println("Fastest per problem");
        for (Map.Entry<Pair<LocalDate, Integer>, List<Pair<Integer, String>>> entry : fastestPerProblem.entrySet()) {
            List<Pair<Integer, String>> list = entry.getValue();
            Collections.sort(list);
            System.out.print(String.format("%s_%d %s", entry.getKey().getKey(), entry.getKey().getValue(),
                    StringUtils.rightPad(list.get(0).getValue(), 13)));
            if (list.size() > 1) {
                System.out.print(String.format(" %s %s", StringUtils.rightPad(list.get(1).getValue(), 14),
                        formatDuration(list.get(1).getKey() - list.get(0).getKey())));
            }
            System.out.println();
        }
    }

    private static String formatDuration(int t) {
        String out = t % 60 + "s";
        t /= 60;
        if (t > 0) {
            out = t % 60 + "m " + out;
        }
        t /= 60;
        if (t > 0) {
            out = t % 24 + "h " + out;
        }
        t /= 24;
        if (t > 0) {
            out = t % 365 + "d " + out;
        }
        t /= 365;
        if (t > 0) {
            out = t + "y " + out;
        }
        return out;
    }

    private static void addToFastestPerProblem(Map<Pair<LocalDate, Integer>, List<Pair<Integer, String>>> fastestPerProblem, Pair<LocalDate, Integer> key, CompletionLevel completionLevel, String name) {
        if (completionLevel != null) {
            LocalDate completionDate = LocalDate.from(
                    Instant.ofEpochSecond(completionLevel.getStarTs).atZone(CET));
            fastestPerProblem.computeIfAbsent(key, (k) -> new ArrayList<>()).add(Pair.of(completionLevel.getStarTs,
                    name));
        }
    }

    private static void addToStarsPerDay(Map<LocalDate, Integer> starsPerDay, CompletionLevel completionLevel) {
        if (completionLevel != null) {
            LocalDate completionDate = LocalDate.from(
                    Instant.ofEpochSecond(completionLevel.getStarTs).atZone(CET));
            starsPerDay.merge(completionDate, 1, Integer::sum);
        }
    }


    public static BufferedReader get(int year) throws IOException {
        Path path = Paths.get("stats_" + year + ".json");
        File file = path.toFile();
        if (file.exists()) {
            Instant modified = Instant.ofEpochMilli(file.lastModified());
            if (Duration.between(modified, Instant.now()).compareTo(Duration.of(4, ChronoUnit.HOURS)) > 0) {
                System.out.println("Refresh cache for " + path);
                file.delete();
            }
        }
        if (!file.exists()) {
            URL url = new URL("https://adventofcode.com/" + year + "/leaderboard/private/view/" + LEADERBOARD + ".json");
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Cookie",
                    "session=" + Files.readString(Path.of("cookie.txt")));
            Files.copy(urlConnection.getInputStream(), path);
        }
        return Files.newBufferedReader(path);
    }

    private static class Stats {
        Map<Integer, Member> members;
    }

    private static class Member {
        String name;
        Map<Integer, CompletionDay> completionDayLevel;
    }

    private static class CompletionDay {
        @SerializedName("1")
        CompletionLevel level1;

        @SerializedName("2")
        CompletionLevel level2;
    }

    private static class CompletionLevel {
        Integer getStarTs;
    }
}
