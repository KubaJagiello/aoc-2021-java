package se.jakub.day2;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;
import static se.jakub.reader.DayReader.inputReader;

public class Main {
    public static void main(String[] args) {
        System.out.println(partOne()); // 1698735
        System.out.println(partTwo()); // 1594785890
    }

    private static int partOne() {
        var horizontal = 0;
        var depth = 0;

        for (var line : inputReader(2, 1).collect(toList())) {
            var s = line.split(" ");
            int x = parseInt(s[1]);

            switch (s[0]) {
                case "up" -> depth -= x;
                case "down" -> depth += x;
                default -> horizontal += x;
            }
        }
        return horizontal * depth;
    }

    private static int partTwo() {
        var horizontal = 0;
        var depth = 0;
        var aim = 0;

        for (var line : inputReader(2, 2).collect(toList())) {
            var s = line.split(" ");
            int x = parseInt(s[1]);

            switch (s[0]) {
                case "up" -> aim -= x;
                case "down" -> aim += x;
                default -> {
                    horizontal += x;
                    depth += x * aim;
                }
            }
        }
        return horizontal * depth;
    }
}
