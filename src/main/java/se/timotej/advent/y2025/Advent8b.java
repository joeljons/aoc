package se.timotej.advent.y2025;

import java.io.IOException;
import java.util.*;

public class Advent8b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent8b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    private long calc(List<String> strs) {
        Map<Box, Set<Box>> circuits = new HashMap<>();
        List<Box> boxes = new ArrayList<>();
        for (String str : strs) {
            List<Long> input = Util.findAllLongs(str);
            Box box = new Box(input.get(0), input.get(1), input.get(2));
            boxes.add(box);
            HashSet<Box> boxSet = new HashSet<>();
            boxSet.add(box);
            circuits.put(box, boxSet);
        }
        List<BoxPair> boxPairs = new ArrayList<>();
        for (int i = 0; i < boxes.size(); i++) {
            for (int j = i + 1; j < boxes.size(); j++) {
                boxPairs.add(new BoxPair(dist2(boxes.get(i), boxes.get(j)), boxes.get(i), boxes.get(j)));
            }
        }
        boxPairs.sort(Comparator.comparingLong(BoxPair::dist2));
        for (BoxPair boxPair : boxPairs) {
            if (!circuits.get(boxPair.a()).contains(boxPair.b())) {
                Set<Box> joinedCircuit = circuits.get(boxPair.a());
                joinedCircuit.addAll(circuits.get(boxPair.b()));
                for (Box connectedBox : circuits.get(boxPair.b())) {
                    circuits.put(connectedBox, joinedCircuit);
                }
                if(joinedCircuit.size() == strs.size()) {
                    return boxPair.a().x() * boxPair.b().x();
                }
            }
        }
        return 0;
    }

    private long dist2(Box from, Box to) {
        return (from.x() - to.x()) * (from.x() - to.x())
                + (from.y() - to.y()) * (from.y() - to.y())
                + (from.z() - to.z()) * (from.z() - to.z());
    }

    private record Box(long x, long y, long z) {
    }

    private record BoxPair(long dist2, Box a, Box b) {
    }
}
