package se.timotej.advent.y2025;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Online {

    private static boolean gotZero = false;
    private static long startNanos;

    public static void main(String[] args) throws IOException {
        List<String> today = getToday();
        for (String line : today) {
            System.out.println(line);
        }
    }

    public static List<String> getToday() throws IOException {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("CET"));
        boolean slept = false;
        while (cal.get(Calendar.HOUR) < 6) {
            slept = true;
            System.out.printf("Zzz %02d:%02d:%02d%n", cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE),
                    cal.get(Calendar.SECOND));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cal = Calendar.getInstance(TimeZone.getTimeZone("CET"));
        }
        if (slept) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return get(day);
    }

    public static List<String> get() throws IOException {
        Pair<Integer, Integer> dayLevel = getDayAndLevel();
        System.out.println("dayLevel = " + dayLevel);
        return get(dayLevel.getLeft());
    }

    public static List<String> get(int day) throws IOException {
        if (day == 0) {
            gotZero = true;
        }
        Path path = Paths.get("input_2025_" + day + ".txt");
        if (!path.toFile().exists()) {
            URL url = new URL("https://adventofcode.com/2025/day/" + day + "/input");
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Cookie",
                    "session=" + Files.readString(Path.of("cookie.txt")));
            Files.copy(urlConnection.getInputStream(), path);
        }
        return readAndStartTimer(path);
    }

    public static List<String> get(int day, String suffix) throws IOException {
        Path path = Paths.get("input_2025_" + day + suffix + ".txt");
        return readAndStartTimer(path);
    }

    private static List<String> readAndStartTimer(Path path) throws IOException {
        List<String> strings = Files.readAllLines(path);
        startNanos = System.nanoTime();
        return strings;
    }

    private static Pair<Integer, Integer> getDayAndLevel() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String className = stackTrace[3].getClassName();
        Matcher matcher = Pattern.compile("(\\d+)(b?)$").matcher(className);
        if (matcher.find()) {
            return Pair.of(
                    Integer.parseInt(matcher.group(1)),
                    matcher.group(2).length() + 1
            );
        } else {
            throw new RuntimeException("Couldn't figure out day and level out of class name " + className);
        }
    }

    public static void submit(long answer) {
        Pair<Integer, Integer> dayLevel = getDayAndLevel();
        System.out.println("dayLevel = " + dayLevel);
        submit(dayLevel.getLeft(), dayLevel.getRight(), String.valueOf(answer));
    }

    public static void submit(int answer) {
        Pair<Integer, Integer> dayLevel = getDayAndLevel();
        System.out.println("dayLevel = " + dayLevel);
        submit(dayLevel.getLeft(), dayLevel.getRight(), String.valueOf(answer));
    }

    public static void submit(String answer) {
        Pair<Integer, Integer> dayLevel = getDayAndLevel();
        System.out.println("dayLevel = " + dayLevel);
        submit(dayLevel.getLeft(), dayLevel.getRight(), answer);
    }

    public static void submit(int day, int level, long answer) {
        submit(day, level, String.valueOf(answer));
    }

    public static void submit(int day, int level, int answer) {
        submit(day, level, String.valueOf(answer));
    }

    public static boolean submit(int day, int level, String answer) {
        System.out.println("answer = " + answer);
        if (gotZero) {
            System.out.println("Will not submit after get(0)");
            return false;
        }
        if (!(day == 25 && level == 2) && ("0".equals(answer) || StringUtils.isEmpty(answer))) {
            System.out.println("Will not submit this answer ('" + answer + "')");
            return false;
        }
        if (startNanos != 0) {
            long durationMs = (System.nanoTime() - startNanos) / 1000000;
            System.out.printf("Time taken: %.3f s%n", durationMs / 1000.0);
        }
        boolean won = false;
        try {
            boolean retry;
            do {
                int sleeptime = 0;
                String data = String.format("level=%d&answer=%s", level, URLEncoder.encode(answer, StandardCharsets.UTF_8));

                URL url = new URL("https://adventofcode.com/2025/day/" + day + "/answer");
                URLConnection urlConnection = url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Cookie",
                        "session=" + Files.readString(Path.of("cookie.txt")));


                OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
                wr.write(data);
                wr.flush();

                BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                boolean print = false;
                retry = false;
                while ((line = rd.readLine()) != null) {
                    if (line.contains("</main>")) {
                        print = false;
                    }
                    if (print) {
                        System.out.println(line);
                        if (line.contains("gold star")) {
                            won = true;
                        }
                    }
                    if (line.contains("<main>")) {
                        print = true;
                    }
                    if (line.contains("You gave an answer too recently")) {
                        retry = true;
                        Matcher matcher = Pattern.compile("You have (\\d+)s left to wait").matcher(line);
                        sleeptime = 20;
                        if (matcher.find()) {
                            sleeptime = Integer.parseInt(matcher.group(1)) + 1;
                        } else {
                            matcher = Pattern.compile("You have (\\d+)m (\\d+)s left to wait").matcher(line);
                            if (matcher.find()) {
                                sleeptime =
                                        Integer.parseInt(matcher.group(1)) * 60 + Integer.parseInt(matcher.group(2)) + 1;
                            }
                        }
                        System.out.println("sleeptime = " + sleeptime);
                    }
                }
                wr.close();
                rd.close();
                if (retry) {
                    try {
                        Thread.sleep(sleeptime * 1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } while (retry);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (won && day == 25 && level == 1) {
            submit(25, 2, 0);
        }
        return won;
    }
}
