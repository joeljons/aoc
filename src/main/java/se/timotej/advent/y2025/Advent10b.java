package se.timotej.advent.y2025;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Advent10b {

    public static final Path BEST_PATH_FILE = Path.of("2025_10_best.txt");

    public static void main(String[] args) throws IOException, InterruptedException {
        int svar = new Advent10b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    Random r = new Random();

    private int calc(List<String> strs) throws IOException {
        int svar = 0;
        int kaputtCount = 0;
        Map<String, Integer> best = new HashMap<>();
        if (BEST_PATH_FILE.toFile().exists()) {
            for (String line : Files.readAllLines(BEST_PATH_FILE)) {
                String[] split = line.split("->");
                best.put(split[1], Integer.parseInt(split[0]));
            }
            System.out.println("Total sum to beat: " + best.values().stream().mapToInt(Integer::intValue).sum());
        }
        for (String str : strs) {
            System.out.println("str = " + str);
            String[] machine = str.split(" ");

            String[] targetStr = cut(machine[machine.length - 1]).split(",");
            target = new int[targetStr.length];
            for (int i = 0; i < target.length; i++) {
                target[i] = Integer.parseInt(targetStr[i]);
            }

            int[][] buttons = new int[machine.length - 2][];
            System.out.println("buttons.length = " + buttons.length);
            for (int buttonI = 1; buttonI < machine.length - 1; buttonI++) {
                String[] buttonStr = cut(machine[buttonI]).split(",");
                buttons[buttonI - 1] = new int[buttonStr.length];
                for (int i = 0; i < buttonStr.length; i++) {
                    buttons[buttonI - 1][i] = Integer.parseInt(buttonStr[i]);
                }
            }
            deltaLimit = buttons.length >= 13 ? 1 : buttons.length >= 10 ? 2 : buttons.length >= 8 ? 3 : 4;
            boolean asGoodAsLastTime = false;
            int bestBestClickCount = best.getOrDefault(str, Integer.MAX_VALUE);
            for (int i = 0; i < 1024; i++) {
                bestClicks = new int[buttons.length];
                bestSums = new int[target.length];
                for (int j = 0; j < bestClicks.length; j++) {
                    if (target[buttons[j][0]] > 0) {
                        bestClicks[j] = r.nextInt(target[buttons[j][0]] * buttons[j].length);
                        for (int k = 0; k < buttons[j].length; k++) {
                            bestSums[buttons[j][k]] += bestClicks[j];
                        }
                    }
                }
                bestDiff = Integer.MAX_VALUE;
                bestClickCount = Integer.MAX_VALUE;
                do {
                    improved = false;
                    int[] clicks = bestClicks;
                    int[] sums = bestSums;
                    rek(buttons, clicks, sums, 0);
                } while (improved);

                if (bestClickCount == Integer.MAX_VALUE || bestDiff != 0) {
                    System.out.print(".");
                } else {
                    System.out.print("!");
                    if (bestClickCount < bestBestClickCount) {
                        System.out.println();
                        System.out.printf("New best! %d -> %d%n", bestBestClickCount, bestClickCount);
                        bestBestClickCount = bestClickCount;
                        best.put(str, bestClickCount);
                        try (FileWriter fileWriter = new FileWriter(BEST_PATH_FILE.toFile())) {
                            for (Map.Entry<String, Integer> stringIntegerEntry : best.entrySet()) {
                                fileWriter.write(String.format("%d->%s%n", stringIntegerEntry.getValue(), stringIntegerEntry.getKey()));
                            }
                        }
                    }
                    if(bestClickCount == bestBestClickCount)
                        asGoodAsLastTime = true;
                    if (buttons.length >= 20 && i > 10 || i > 50) {
                        break;
                    }
                }
            }
            System.out.println();
            if (bestBestClickCount == Integer.MAX_VALUE) {
                System.out.println("Hittade ingen bra lösning");
                kaputtCount++;
            } else {
                System.out.println("bestBestClickCount = " + bestBestClickCount );
                if(asGoodAsLastTime) {
                    System.out.println("(as good as last time)");
                } else {
                    System.out.println("(worse)");
                }
                svar += bestBestClickCount;
                System.out.println("svar = " + svar);
            }
        }
        if (kaputtCount > 0) {
            throw new RuntimeException("Kaputt " + kaputtCount);
        }
        return svar;
    }

    int deltaLimit;
    int[] bestClicks;
    int[] bestSums;

    int[] target;
    boolean improved;
    int bestDiff;
    int bestClickCount;

    private void rek(int[][] buttons, int[] clicks, int[] sums, int buttonI) {
        if (buttonI == buttons.length) {
            int diff = 0;
            int clickCount = 0;
            for (int i = 0; i < sums.length; i++) {
                diff += Math.abs(target[i] - sums[i]);
            }
            for (int i = 0; i < clicks.length; i++) {
                clickCount += clicks[i];
            }
            if (diff < bestDiff || (diff == bestDiff && clickCount < bestClickCount)) {
                improved = true;
                bestDiff = diff;
                bestClickCount = clickCount;
                bestClicks = Arrays.copyOf(clicks, clicks.length);
                bestSums = Arrays.copyOf(sums, sums.length);
            }
            return;
        }
        for (int delta = -deltaLimit; delta <= deltaLimit; delta++) {
            if (clicks[buttonI] + delta < 0) {
                continue;
            }
            clicks[buttonI] += delta;
            for (int joltI : buttons[buttonI]) {
                sums[joltI] += delta;
            }
            rek(buttons, clicks, sums, buttonI + 1);
            clicks[buttonI] -= delta;
            for (int joltI : buttons[buttonI]) {
                sums[joltI] -= delta;
            }
        }
    }

    private String cut(String s) {
        return s.substring(1, s.length() - 1);
    }
}
