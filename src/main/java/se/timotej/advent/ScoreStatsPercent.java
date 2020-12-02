package se.timotej.advent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScoreStatsPercent {

    public static final int YEAR = 2020;

    public static void main(String[] args) throws IOException {
        LocalTime[][] worstTimes = getWorstSolveTimes();

        System.out.println("      --------------Part 1-------------   --------------Part 2-------------");
        System.out.println("Day          Time           Rank  Score          Time           Rank  Score");
        try (BufferedReader br = new BufferedReader(new FileReader("personal_stats_" + YEAR + ".txt"))) {
            String str;
            Pattern pattern = Pattern.compile("\\s*(?<day>\\d+)\\s+(?<time1>\\d\\d:\\d\\d:\\d\\d|\\s*>24h)\\s+(?<rank1>\\d+)\\s+(?<score1>\\d+)\\s+(?<time2>\\d\\d:\\d\\d:\\d\\d|\\s*>24h)\\s+(?<rank2>\\d+)\\s+(?<score2>\\d+)\\s*");
            while ((str = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(str);
                if (matcher.matches()) {
                    int day = Integer.parseInt(matcher.group("day"));
                    System.out.printf("%3d", day);
                    for (int part = 1; part <= 2; part++) {
                        String solvePercentOfWorst = "";
                        if (!matcher.group("time" + part).contains(">24h") && worstTimes[day - 1][part - 1] != null) {
                            LocalTime mySolveTime = LocalTime.parse(matcher.group("time" + part));
                            solvePercentOfWorst = String.format("(%7.1f%%)", 100f * mySolveTime.toNanoOfDay() / worstTimes[day - 1][part - 1].toNanoOfDay());
                        }
                        System.out.printf("   %8s %10s%6s%7s",
                                matcher.group("time" + part),
                                solvePercentOfWorst,
                                matcher.group("rank" + part),
                                matcher.group("score" + part));
                    }
                    System.out.println();
                }
            }
        }
    }

    private static LocalTime[][] getWorstSolveTimes() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("scores_" + YEAR + ".txt"))) {
            String str;
            Pattern pattern = Pattern.compile("(...)\\) Dec (\\d\\d)  (\\d\\d:\\d\\d:\\d\\d) .*");
            LocalTime[][] worstTimes = new LocalTime[25][2];
            while ((str = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(str);
                if (matcher.matches()) {
                    int placement = Integer.parseInt(matcher.group(1).trim());
                    if (placement == 100) {
                        int day = Integer.parseInt(matcher.group(2).trim());
                        String solveTime = matcher.group(3);
                        worstTimes[day - 1][worstTimes[day - 1][1] == null ? 1 : 0] = LocalTime.parse(solveTime);
                    }
                }
            }
            return worstTimes;
        }
    }

}
