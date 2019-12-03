package se.timotej.advent.y2017;

import org.apache.commons.lang3.StringUtils;

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
    public static List<String> get(int day) throws IOException {
        Path path = Paths.get("input_2017_" + day + ".txt");
        if (!path.toFile().exists()) {
            URL url = new URL("https://adventofcode.com/2017/day/" + day + "/input");
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Cookie",
                    "session=" + Files.readString(Path.of("cookie.txt")));
            Files.copy(urlConnection.getInputStream(), path);
        }
        return Files.readAllLines(path);
    }


    public static List<String> get(int day, String suffix) throws IOException {
        Path path = Paths.get("input_2017_" + day + suffix + ".txt");
        return Files.readAllLines(path);
    }

    public static void submit(int day, int level, int answer) {
        submit(day, level, String.valueOf(answer));
    }

    public static void submit(int day, int level, String answer) {
        System.out.println("answer = " + answer);
        if("0".equals(answer) || StringUtils.isEmpty(answer)){
            System.out.println("Will not submit this answer");
            return;
        }
        try {
            boolean retry = false;
            do {
                int sleeptime = 0;
                String data = String.format("level=%d&answer=%s", level, URLEncoder.encode(answer, "UTF-8"));

                URL url = new URL("https://adventofcode.com/2017/day/" + day + "/answer");
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
    }
}
