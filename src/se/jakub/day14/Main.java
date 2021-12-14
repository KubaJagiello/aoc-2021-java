package se.jakub.day14;

import java.util.HashMap;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.IntStream.range;
import static se.jakub.reader.DayReader.inputReader;


public class Main {
    public static void main(String[] args) {
        System.out.println(partOneAndTwo(10)); // 3411
        System.out.println(partOneAndTwo(40)); // 7477815755570
    }

    private static long partOneAndTwo(int maxSteps) {
        var l = inputReader(14, 1).collect(toList());
        var s = l.get(0);
        var rules = range(2, l.size())
                .mapToObj(i -> l.get(i).split(" -> "))
                .collect(toMap(split -> split[0], split -> split[1], (a, b) -> b, HashMap::new));
        var dp = new HashMap<String, Long>();
        var count = new HashMap<Character, Long>();

        for (int i = 1; i < s.length(); i++) {
            var sub = s.substring(i - 1, i + 1);
            dp.put(sub, 1 + dp.getOrDefault(sub, 0L));
            count.put(sub.charAt(0), 1 + count.getOrDefault(sub.charAt(0), 0L));
            count.put(sub.charAt(1), 1 + count.getOrDefault(sub.charAt(1), 0L));
        }

        for (int step = 0; step < maxSteps; step++) {
            var newDp = new HashMap<>(dp);

            for (var seq : dp.entrySet()) {
                if (rules.containsKey(seq.getKey())) {
                    var newC = rules.get(seq.getKey());
                    var newLeft = seq.getKey().charAt(0) + newC;
                    var newRight = newC + seq.getKey().charAt(1);
                    var timesItAlreadyExist = count.getOrDefault(newC.charAt(0), 0L);
                    var numberOfSequencesThatWillBeReplaced = seq.getValue();

                    count.put(newC.charAt(0), timesItAlreadyExist + numberOfSequencesThatWillBeReplaced);
                    newDp.put(newLeft, seq.getValue() + newDp.getOrDefault(newLeft, 0L));
                    newDp.put(newRight, seq.getValue() + newDp.getOrDefault(newRight, 0L));
                    newDp.put(seq.getKey(), Math.max(0L, newDp.getOrDefault(seq.getKey(), 0L) - seq.getValue()));
                }
            }
            dp = newDp;
        }
        return count.values().stream().max(Long::compareTo).get() - count.values().stream().min(Long::compareTo).get();
    }
}
