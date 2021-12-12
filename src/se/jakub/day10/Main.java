package se.jakub.day10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static se.jakub.reader.DayReader.inputReader;


public class Main {
    public static void main(String[] args) {
        System.out.println(partOne()); // 345441
        System.out.println(partTwo()); // 3235371166
    }

    private static long partOne() {
        var rMap = getRightToLeftMapping();
        var rCost = getRightCost(3, 57, 1197, 25137);
        var cost = 0;

        for (var line : inputReader(10, 1).collect(toList())) {
            var linked = new LinkedList<String>();
            for (var c : line.toCharArray()) {
                var m = String.valueOf(c);
                if ("({[<".contains(m)) {
                    linked.addLast(m);
                } else {
                    if (!rMap.get(m).equals(linked.getLast())) {
                        cost += rCost.get(m);
                        break;
                    }
                    linked.removeLast();
                }
            }
        }
        return cost;
    }


    private static Long partTwo() {
        var lMap = getLeftToRightMapping();
        var rMap = getRightToLeftMapping();
        var partTwo = new ArrayList<String>();

        for (var line : inputReader(10, 2).collect(toList())) {
            var partTwoFilter = true;
            var linked = new LinkedList<String>();
            for (var c : line.toCharArray()) {
                var m = String.valueOf(c);
                if ("({[<".contains(m)) {
                    linked.addLast(m);
                } else {
                    if (!rMap.get(m).equals(linked.getLast())) {
                        partTwoFilter = false;
                        break;
                    }
                    linked.removeLast();
                }
            }
            if (partTwoFilter) {
                partTwo.add(line);
            }
        }

        var rAuto = getRightCost(1, 2, 3, 4);
        var scores = new ArrayList<Long>();

        for (var line : partTwo) {
            var linked = new LinkedList<String>();
            for (var c : line.toCharArray()) {
                var m = String.valueOf(c);
                if ("({[<".contains(m)) {
                    linked.addLast(m);
                } else {
                    linked.removeLast();
                }

            }
            var incompleteScore = 0L;
            for (int i = linked.size() - 1; i >= 0; i--) {
                incompleteScore *= 5;
                incompleteScore += rAuto.get(lMap.get(linked.get(i)));
            }
            scores.add(incompleteScore);
        }

        Collections.sort(scores);
        return scores.get(scores.size() / 2);
    }

    private static Map<String, String> getLeftToRightMapping() {
        return Map.of(
                "(", ")",
                "[", "]",
                "{", "}",
                "<", ">"
        );
    }

    private static Map<String, String> getRightToLeftMapping() {
        return Map.of(
                ")", "(",
                "]", "[",
                "}", "{",
                ">", "<"
        );
    }

    private static Map<String, Integer> getRightCost(int i, int i2, int i3, int i4) {
        return Map.of(
                ")", i,
                "]", i2,
                "}", i3,
                ">", i4
        );
    }
}
