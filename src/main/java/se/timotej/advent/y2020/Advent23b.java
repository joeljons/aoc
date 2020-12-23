package se.timotej.advent.y2020;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Advent23b {

    public static void main(String[] args) throws IOException {
        long svar = new Advent23b().calc(Online.get());
        System.out.println("svar = " + svar);
        Online.submit(svar);
    }

    Map<Integer, Node> map = new HashMap<>();

    private long calc(List<String> strs) {
        String now = strs.get(0);

        List<Integer> g = new LinkedList<>();
        for (int i = 0; i < now.length(); i++) {
            char c = now.charAt(i);
            g.add(c - '0');
        }
        while (g.size() < 1000000) {
            g.add(g.size() + 1);
        }
        List<Node> nodes = new LinkedList<>();
        Node lastNode = null;
        for (Integer value : g) {
            final Node node = new Node();
            node.value = value;
            map.put(value, node);
            if (lastNode != null) {
                lastNode.next = node;
            }
            nodes.add(node);
            lastNode = node;
        }
        lastNode.next = nodes.get(0);

        Node current = nodes.get(0);

        for (int i = 0; i < 10000000; i++) {
            current = move(current);
        }

        Node one = map.get(1);
        Node next = one.next;
        Node next2 = next.next;
        long svar = next.value;
        svar *= next2.value;
        return svar;
    }

    private Node move(Node node) {
        Node pickup = node.next;
        node.next = node.next.next.next.next;
        List<Integer> pickupValues = new ArrayList<>();
        pickupValues.add(pickup.value);
        pickupValues.add(pickup.next.value);
        pickupValues.add(pickup.next.next.value);
        int destination = node.value - 1;
        if (destination == 0) {
            destination = 1000000;
        }
        while (pickupValues.contains(destination)) {
            destination--;
            if (destination == 0) {
                destination = 1000000;
            }
        }
        Node dest = map.get(destination);
        pickup.next.next.next = dest.next;
        dest.next = pickup;
        return node.next;
    }

    private static class Node {
        int value;
        Node next;
    }
}