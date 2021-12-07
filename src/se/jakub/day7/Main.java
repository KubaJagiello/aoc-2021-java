package se.jakub.day7;

import java.util.Arrays;

import static java.lang.Math.abs;
import static java.lang.Math.min;
import static java.util.stream.Collectors.*;
import static se.jakub.reader.DayReader.inputReader;


public class Main {
    public static void main(String[] args) {
        System.out.println(partOne()); // 356958
        System.out.println(partTwo()); // 105461913
    }

    private static int partOne() {
        var l = inputReader(7, 1).collect(toList());
        var n = Arrays.stream(l.get(0).split(","))
                .map(Integer::parseInt)
                .collect(toList());

        var map = n.stream().collect(groupingBy(v -> v, counting()));
        var maxX = n.stream().max(Integer::compare).get();

        var totalFuel = Integer.MAX_VALUE;
        for (int a = 0; a < maxX + 1; a++) {
            var fuel = 0;
            for (var b : map.keySet()) {
                fuel += abs(a - b) * map.getOrDefault(b, 1L);
            }
            totalFuel = min(fuel, totalFuel);
        }
        return totalFuel;
    }

    private static int partTwo() {
        var l = inputReader(7, 2).collect(toList());
        var n = Arrays.stream(l.get(0).split(","))
                .map(Integer::parseInt)
                .collect(toList());

        var map = n.stream().collect(groupingBy(v -> v, counting()));
        var maxX = n.stream().max(Integer::compare).get();

        var totalFuel = Integer.MAX_VALUE;
        for (int a = 0; a < maxX + 1; a++) {
            var fuel = 0;
            for (var b : map.keySet()) {
                var dist = abs(a - b);
                fuel += (long) dist * (dist + 1) / 2 * map.getOrDefault(b, 1L);
            }
            totalFuel = min(fuel, totalFuel);
        }
        return totalFuel;
    }
}
