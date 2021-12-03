package se.jakub.day3;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.Integer.*;
import static java.util.stream.Collectors.toList;
import static se.jakub.reader.DayReader.inputReader;

public class Main {
    public static void main(String[] args) {
        System.out.println(partOne()); // 2743844
        System.out.println(partTwo()); // 6677951
    }

    private static int partOne() {
        var lines = inputReader(3, 1).collect(toList());
        var gamma = "";

        for (int i = 0; i < lines.get(0).length(); i++) {
            var h = 0;
            for (var line : lines) {
                h += line.charAt(i) == '1' ? 1 : -1;
            }
            gamma += h > 0 ? "1" : "0";
        }
        return parseInt(gamma, 2) * parseInt(toBinaryString(~parseInt(gamma, 2)), 32 - gamma.length(), 32, 2);
    }

    private static int partTwo() {
        var lines = inputReader(3, 2).collect(toList());
        var oxygen = new ArrayList<>(lines);
        var c02 = new ArrayList<>(lines);

        return parseInt(partTwoRating(oxygen, p -> p >= 0), 2) * parseInt(partTwoRating(c02, p -> p < 0), 2);
    }

    private static String partTwoRating(ArrayList<String> list, Predicate<Integer> predicate) {
        for (int i = 0; i < list.get(0).length() && list.size() > 1; i++) {
            int finalI = i;
            var h = list.stream().mapToInt(v -> v.charAt(finalI) == '1' ? 1 : -1).sum();
            var c = predicate.test(h) ? '1' : '0';
            list = (ArrayList<String>) list.stream().filter(v -> v.charAt(finalI) == c).collect(toList());
        }
        return list.get(0);
    }
}
