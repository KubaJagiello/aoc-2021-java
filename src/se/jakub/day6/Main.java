package se.jakub.day6;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static se.jakub.reader.DayReader.inputReader;


public class Main {
    public static void main(String[] args) {
        System.out.println(partOne()); // 388419
        System.out.println(partTwo()); // 1740449478328
    }

    private static long partOne() {
        return calculatePopulation(inputReader(6, 1).collect(toList()), 80);
    }

    private static long partTwo() {
        return calculatePopulation(inputReader(6, 2).collect(toList()), 256);
    }

    private static long calculatePopulation(List<String> l, int days) {
        var n = Arrays.stream(l.get(0).split(",")).toList().stream()
                .map(Long::parseLong)
                .collect(toList());
        var map = new HashMap<Long, Long>();

        for (var number : n) {
            map.put(number, 1 + map.getOrDefault(number, 0L));
        }

        for (long i = 0; i < days; i++) {
            var newMap = new HashMap<Long, Long>();
            for (var k : map.keySet()) {
                var number = map.get(k);
                if (k == 0) {
                    newMap.put(8L, number);
                    newMap.put(6L, number);
                } else {
                    newMap.put(k - 1, number + newMap.getOrDefault(k - 1, 0L));
                }
            }
            map = newMap;
        }
        return map.values().stream().mapToLong(v -> v).sum();
    }
}