package se.jakub.day5;

import java.util.HashMap;
import java.util.Objects;

import static java.lang.Integer.max;
import static java.lang.Integer.parseInt;
import static java.lang.Math.min;
import static java.util.stream.Collectors.toList;
import static se.jakub.reader.DayReader.inputReader;


public class Main {
    public static void main(String[] args) {
        System.out.println(partOne()); // 4745
        System.out.println(partTwo()); // 18442
    }

    private static int partOne() {
        var lines = inputReader(5, 1)
                .map(v -> v.replaceAll(" -> ", ","))
                .collect(toList());
        var points = new HashMap<Point, Integer>();

        for (var line : lines) {
            var t = line.split(",");
            var x1 = parseInt(t[0]);
            var y1 = parseInt(t[1]);
            var x2 = parseInt(t[2]);
            var y2 = parseInt(t[3]);

            if (x1 != x2 && y1 != y2)
                continue;

            for (int i = min(x1, x2); i < max(x1, x2) + 1; i++) {
                for (int j = min(y1, y2); j < max(y1, y2) + 1; j++) {
                    var p = new Point(i, j);
                    points.put(p, 1 + points.getOrDefault(p, 0));
                }
            }
        }
        return (int) points.values().stream().filter(i -> i > 1).count();
    }

    private static int partTwo() {
        var lines = inputReader(5, 2)
                .map(v -> v.replaceAll(" -> ", ","))
                .collect(toList());
        var points = new HashMap<Point, Integer>();

        for (var line : lines) {
            var t = line.split(",");
            var x1 = parseInt(t[0]);
            var y1 = parseInt(t[1]);
            var x2 = parseInt(t[2]);
            var y2 = parseInt(t[3]);

            if (x1 == x2 || y1 == y2) {
                for (int i = min(x1, x2); i < max(x1, x2) + 1; i++) {
                    for (int j = min(y1, y2); j < max(y1, y2) + 1; j++) {
                        var p = new Point(i, j);
                        points.put(p, 1 + points.getOrDefault(p, 0));
                    }
                }
            } else {
                var k = x1 < x2 ? 1 : -1;
                var m = y1 < y2 ? 1 : -1;

                while (x1 != (x2 + k)) {
                    var p = new Point(x1, y1);
                    points.put(p, 1 + points.getOrDefault(p, 0));
                    x1 += k;
                    y1 += m;
                }
            }
        }
        return (int) points.values().stream().filter(i -> i > 1).count();
    }
}

record Point(int x, int y) {
}
