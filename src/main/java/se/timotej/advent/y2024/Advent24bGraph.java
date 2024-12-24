package se.timotej.advent.y2024;

import java.io.IOException;
import java.util.List;

public class Advent24bGraph {

    public static void main(String[] args) throws IOException {
        new Advent24bGraph().generateGraph(Online.get(24));
    }

    private void generateGraph(List<String> strs) {
        List<List<String>> input = Util.splitByDoubleNewline(strs);
        System.out.println("digraph G {");
        for (String s : input.getLast()) {
            String[] split = s.split(" ");
            System.out.printf("%s -> %s [headlabel=\"%s\"]%n",split[0],split[4],split[1]);
            System.out.printf("%s -> %s [headlabel=\"%s\"]%n", split[2], split[4], split[1]);
        }
        System.out.println("}");
    }
}
