package se.jakub.day8;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static se.jakub.reader.DayReader.inputReader;


public class Main {

    public static void main(String[] args) {
        System.out.println(partOne()); // 493
        System.out.println(partTwo()); // 1010460
    }

    private static int partOne() {
        return inputReader(8, 2)
                .map(line -> line.split(" \\| ")[1])
                .mapToInt(m -> (int) Arrays.stream(m.split(" "))
                        .mapToInt(String::length)
                        .filter(tmp -> tmp == 2 || tmp == 4 || tmp == 3 || tmp == 7)
                        .count())
                .sum();
    }

    private static int partTwo() {
        var answer = 0;

        for (var line : inputReader(8, 2).collect(toList())) {
            var first = line.split(" \\| ")[0];
            var second = line.split(" \\| ")[1];

            var set = new HashSet<>(Arrays.asList(first.split(" ")));
            var seqToNumber = new HashMap<String, String>();

            var one = getByLength(set, 2);
            var four = getByLength(set, 4);
            seqToNumber.put(one, "1");
            seqToNumber.put(four, "4");
            seqToNumber.put(getByLength(set, 3), "7");
            seqToNumber.put(getByLength(set, 7), "8");

            var fiveLengths = (HashSet<String>) set.stream().filter(v -> v.length() == 5).collect(toSet());
            seqToNumber.put(removeByNumberOfSimilarities(fiveLengths, one, 2), "3");
            seqToNumber.put(removeByNumberOfSimilarities(fiveLengths, four, 3), "5");
            seqToNumber.put(fiveLengths.iterator().next(), "2");

            var sixLengths = (HashSet<String>) set.stream().filter(v -> v.length() == 6).collect(toSet());
            seqToNumber.put(removeByNumberOfSimilarities(sixLengths, one, 1), "6");
            seqToNumber.put(removeByNumberOfSimilarities(sixLengths, four, 4), "9");
            seqToNumber.put(sixLengths.iterator().next(), "0");

            var outputValue = "";
            for (var x : second.split(" ")) {
                for (var digit : seqToNumber.keySet().stream().toList()) {
                    if (areEqual(digit, x)) {
                        outputValue += seqToNumber.get(digit);
                    }
                }
            }
            answer += Integer.parseInt(outputValue);
        }
        return answer;
    }

    private static boolean areEqual(String a, String b) {
        return b.chars().boxed().collect(toSet())
                .equals(a.chars().boxed().collect(toSet()));
    }

    public static String removeByNumberOfSimilarities(HashSet<String> set, String s, int similarities) {
        var a = set.stream().filter(v -> similarities(v, s) == similarities).collect(toList());
        set.remove(a.get(0));
        return a.get(0);
    }

    public static int similarities(String a, String b) {
        var s = 0;
        for (char c : b.toCharArray()) {
            if (a.contains(String.valueOf(c))) {
                s++;
            }
        }
        return s;
    }

    public static String getByLength(HashSet<String> set, int length) {
        return set.stream().filter(s -> s.length() == length).findFirst().orElse("");
    }
}
