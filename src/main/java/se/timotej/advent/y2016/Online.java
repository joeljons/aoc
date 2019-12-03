package se.timotej.advent.y2016;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Online {

    public static List<String> get() throws IOException {
        Pair<Integer, Integer> dayLevel = getDayAndLevel();
        System.out.println("dayLevel = " + dayLevel);
        return get(dayLevel.getLeft());
    }

    public static List<String> get(int day) throws IOException {
        Path path = Paths.get("input_2016_" + day + ".txt");
        if (!path.toFile().exists()) {
            URL url = new URL("https://adventofcode.com/2016/day/" + day + "/input");
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Cookie",
                    "session=" + Files.readString(Path.of("cookie.txt")));
            Files.copy(urlConnection.getInputStream(), path);
        }
        return Files.readAllLines(path);
    }


    public static List<String> get(int day, String suffix) throws IOException {
        Path path = Paths.get("input_2016_" + day + suffix + ".txt");
        return Files.readAllLines(path);
    }

    private static Pair<Integer, Integer> getDayAndLevel() {
        StackTraceElement[] stackTrace = new Exception().getStackTrace();
        String className = stackTrace[2].getClassName();
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

    public static void submit(int day, int level, int answer) {
        submit(day, level, String.valueOf(answer));
    }

    public static boolean submit(int day, int level, String answer) {
        System.out.println("answer = " + answer);
        if (!(day == 25 && level == 2) && ("0".equals(answer) || StringUtils.isEmpty(answer))) {
            System.out.println("Will not submit this answer ('" + answer + "')");
            return false;
        }
        boolean won = false;
        try {
            boolean retry = false;
            do {
                int sleeptime = 0;
                String data = String.format("level=%d&answer=%s", level, URLEncoder.encode(answer, "UTF-8"));

                URL url = new URL("https://adventofcode.com/2016/day/" + day + "/answer");
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
                        Thread.sleep(sleeptime * 1000);
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
